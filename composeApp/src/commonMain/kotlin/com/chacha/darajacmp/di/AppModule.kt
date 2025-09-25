package com.chacha.darajacmp.di

import com.chacha.darajacmp.network.DarajaApiCallService
import com.chacha.darajacmp.utils.getHttpClientEngine
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    
    single<HttpClientEngine> { getHttpClientEngine() }
    
    // HttpClient configuration
    single<HttpClient> {
        HttpClient(get()) {
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
                level = LogLevel.INFO
            }
            
            install(HttpTimeout) {
                requestTimeoutMillis = 30000
                connectTimeoutMillis = 10000
                socketTimeoutMillis = 30000
            }
            
            install(HttpRequestRetry) {
                maxRetries = 3

            }
        }
    }
    
    // Daraja API Service
    singleOf(::DarajaApiCallService)
}
