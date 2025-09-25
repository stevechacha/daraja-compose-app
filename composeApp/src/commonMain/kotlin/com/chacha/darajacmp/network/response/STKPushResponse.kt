package com.chacha.darajacmp.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class STKPushResponse(
    @SerialName("MerchantRequestID")
    val merchantRequestID: String,
    @SerialName("CheckoutRequestID")
    val checkoutRequestID: String,
    @SerialName("ResponseCode")
    val responseCode: String,
    @SerialName("ResponseDescription")
    val responseDescription: String,
    @SerialName("CustomerMessage")
    val customerMessage: String
)
