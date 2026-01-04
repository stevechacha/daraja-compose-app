package com.chacha.darajacmp.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// STK Query Models
@Serializable
data class STKQueryRequest(
    @SerialName("BusinessShortCode")
    val businessShortCode: String,
    @SerialName("Password")
    val password: String,
    @SerialName("Timestamp")
    val timestamp: String,
    @SerialName("CheckoutRequestID")
    val checkoutRequestID: String
)
