package com.chacha.darajacmp.network

import com.chacha.darajacmp.models.AuthResponse
import com.chacha.darajacmp.models.STKPushRequest
import com.chacha.darajacmp.models.STKQueryRequest
import com.chacha.darajacmp.network.response.STKPushResponse
import com.chacha.darajacmp.network.response.STKQueryResponse
import com.chacha.darajacmp.utils.DarajaConfig
import com.chacha.darajacmp.utils.DarajaResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*

class DarajaApiCallService(
    private val httpClient: HttpClient,
    private val consumerKey: String,
    private val consumerSecret: String
) {
    internal suspend fun fetchAccessToken(): DarajaResult<AuthResponse> = dataResultSafeApiCall {
        val key = "$consumerKey:$consumerSecret"
        val base64Key = key.encodeBase64()

        val response = httpClient.get(DarajaConfig.SANDBOX_AUTH_URL) {
            headers {
                append(HttpHeaders.Authorization, "Basic $base64Key")
                append(HttpHeaders.Accept, "application/json")
            }
        }

        response.body<AuthResponse>()
    }

    internal suspend fun stkPush(stkPushRequest: STKPushRequest): DarajaResult<STKPushResponse> =
        dataResultSafeApiCall {
            when (val tokenResult = fetchAccessToken()) {
                is DarajaResult.Success -> {
                    val accessToken = tokenResult.data.accessToken

                    val response = httpClient.post(DarajaConfig.SANDBOX_STK_PUSH_URL) {
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $accessToken")
                            append(HttpHeaders.ContentType, "application/json")
                        }
                        setBody(stkPushRequest)
                    }

                    response.body<STKPushResponse>()
                }

                is DarajaResult.Error -> {
                    throw Exception("Failed to get access token: ${tokenResult.error}")
                }
            }
        }

    internal suspend fun stkQuery(stkQueryRequest: STKQueryRequest): DarajaResult<STKQueryResponse> =
        dataResultSafeApiCall {
            when (val tokenResult = fetchAccessToken()) {
                is DarajaResult.Success -> {
                    val accessToken = tokenResult.data.accessToken

                    val response = httpClient.post(DarajaConfig.SANDBOX_STK_QUERY_URL) {
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $accessToken")
                            append(HttpHeaders.ContentType, "application/json")
                        }
                        setBody(stkQueryRequest)
                    }
                    response.body<STKQueryResponse>()
                }

                is DarajaResult.Error -> {
                    throw Exception("Failed to get access token: ${tokenResult.error}")

                }
            }

        }
}