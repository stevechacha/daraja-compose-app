package com.chacha.darajacmp.network

import com.chacha.darajacmp.models.AuthResponse
import com.chacha.darajacmp.utils.DarajaResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
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
            println("🔐 Authenticating with Daraja API...")
            println("   Client ID: ${clientId.take(10)}...")
            
            val response = client.get("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials") {
                headers {
                    append(HttpHeaders.Accept, "application/json")
                }
                basicAuth(clientId, clientSecret)
            }
            
            println("📡 HTTP Status: ${response.status}")
            println("📡 Response Headers: ${response.headers}")
            
            val responseText = response.body<String>()
            println("📡 Raw response: '$responseText'")
            
            if (response.status.value >= 400) {
                throw Exception("HTTP ${response.status.value}: $responseText")
            }
            
            if (responseText.isBlank()) {
                throw Exception("Empty response from server")
            }
            
            // Parse JSON manually to avoid reflection issues
            val json = Json { 
                ignoreUnknownKeys = true
                isLenient = true
            }
            
            val authResponse = json.decodeFromString<AuthResponse>(responseText)
            
            println("✅ Authentication successful!")
            println("   Token: ${authResponse.accessToken.take(20)}...")
            println("   Expires in: ${authResponse.expiresIn} seconds")
            
            // Store token (simplified - no expiry tracking for now)
            accessToken = authResponse.accessToken
            tokenExpiryTime = null // Disable expiry checking for now
            
            DarajaResult.Success(authResponse)
        } catch (e: Exception) {
            println("❌ Authentication failed: ${e.message}")
            e.printStackTrace()
            DarajaResult.Error("Authentication failed: ${e.message}")
        }
    }
    
    suspend fun getValidToken(clientId: String, clientSecret: String): String? {
        // Check if current token is valid
        if (accessToken != null && tokenExpiryTime != null) {
            val currentTime = Clock.System.now()
            if (currentTime < tokenExpiryTime!!) {
                return accessToken
            }
        }
        
        // Token expired or doesn't exist, get new one
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
