package com.chacha.darajacmp.data.remote

import com.chacha.darajacmp.data.dto.AuthResponse
import com.chacha.darajacmp.utils.AppLogger
import com.chacha.darajacmp.utils.DarajaConfig
import com.chacha.darajacmp.utils.DarajaResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json

class AuthService {
    private var accessToken: String? = null
    private var tokenExpiryTime: Instant? = null
    
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                encodeDefaults = true
                coerceInputValues = true
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }
    
    suspend fun authenticate(clientId: String, clientSecret: String): DarajaResult<AuthResponse> {
        return try {
            val cleanClientId = clientId.trim().removeSurrounding("\"").trim()
            val cleanClientSecret = clientSecret.trim().removeSurrounding("\"").trim()
            
            if (cleanClientId.isBlank()) {
                return DarajaResult.Error("Client ID is empty. Please configure your credentials in local.properties")
            }
            if (cleanClientSecret.isBlank()) {
                return DarajaResult.Error("Client Secret is empty. Please configure your credentials in local.properties")
            }
            
            AppLogger.debug("Authenticating with Daraja API")
            
            val key = "$cleanClientId:$cleanClientSecret"
            val base64Key = key.encodeBase64()
            val authUrl = DarajaConfig.SANDBOX_AUTH_URL
            
            val response = client.get(authUrl) {
                headers {
                    append(HttpHeaders.Authorization, "Basic $base64Key")
                    append(HttpHeaders.Accept, "application/json")
                }
            }
            
            val responseText = try {
                response.body<String>()
            } catch (e: Exception) {
                AppLogger.error("Failed to read response body", e)
                return DarajaResult.Error("Error reading response body: ${e.message}")
            }
            
            if (response.status.value >= 400) {
                val responseHeaders = response.headers.toMap()
                AppLogger.warn("Authentication failed with status ${response.status.value}")
                AppLogger.debug("Response headers: $responseHeaders")
                
                val errorMessage = when {
                    response.status.value == 400 && responseText.isBlank() -> {
                        "HTTP 400: Bad Request. Please verify your credentials in local.properties"
                    }
                    response.status.value == 401 -> {
                        "HTTP 401: Unauthorized. Invalid credentials. Please check your Client ID and Client Secret."
                    }
                    responseText.isBlank() -> {
                        "HTTP ${response.status.value}: Empty response. Please check your credentials in local.properties file."
                    }
                    else -> {
                        "HTTP ${response.status.value}: $responseText"
                    }
                }
                return DarajaResult.Error(errorMessage)
            }
            
            if (responseText.isBlank()) {
                return DarajaResult.Error("Empty response from server. Please check your network connection and credentials.")
            }
            
            val json = Json { 
                ignoreUnknownKeys = true
                isLenient = true
            }
            
            val authResponse = json.decodeFromString<AuthResponse>(responseText)
            
            AppLogger.info("Authentication successful")
            
            accessToken = authResponse.accessToken
            tokenExpiryTime = null
            
            DarajaResult.Success(authResponse)
        } catch (e: Exception) {
            AppLogger.error("Authentication failed", e)
            val errorMessage = when {
                e.message?.contains("401") == true -> {
                    "Authentication failed: Invalid credentials. Please check your Client ID and Client Secret in local.properties"
                }
                e.message?.contains("Network") == true || e.message?.contains("connection") == true -> {
                    "Network error: Please check your internet connection"
                }
                else -> {
                    "Authentication failed: ${e.message ?: "Unknown error"}. Please check your credentials and network connection."
                }
            }
            DarajaResult.Error(errorMessage)
        }
    }
    
    suspend fun getValidToken(clientId: String, clientSecret: String): String? {
        if (accessToken != null && tokenExpiryTime != null) {
            val currentTime = Clock.System.now()
            if (currentTime < tokenExpiryTime!!) {
                return accessToken
            }
        }
        
        return when (val authResult = authenticate(clientId, clientSecret)) {
            is DarajaResult.Success -> authResult.data.accessToken
            is DarajaResult.Error -> null
        }
    }
    
    fun clearToken() {
        accessToken = null
        tokenExpiryTime = null
    }
    
    fun isTokenValid(): Boolean {
        return accessToken != null && tokenExpiryTime != null && Clock.System.now() < tokenExpiryTime!!
    }
}

