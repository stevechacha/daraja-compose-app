package com.chacha.darajacmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Authentication Models
@Serializable
data class AuthRequest(
    @SerialName("client_id")
    val clientId: String,
    @SerialName("client_secret")
    val clientSecret: String,
    @SerialName("grant_type")
    val grantType: String = "client_credentials"
)

@Serializable
data class AuthResponse(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expires_in")
    val expiresIn: String
)


// Error Response
@Serializable
data class ErrorResponse(
    @SerialName("requestId")
    val requestId: String? = null,
    @SerialName("errorCode")
    val errorCode: String? = null,
    @SerialName("errorMessage")
    val errorMessage: String
)

