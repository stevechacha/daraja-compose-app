package com.chacha.darajacmp.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// B2B Express CheckOut Models
@Serializable
data class B2BExpressCheckOutRequest(
    @SerialName("primaryShortCode")
    val primaryShortCode: String,
    @SerialName("receiverShortCode")
    val receiverShortCode: String,
    @SerialName("amount")
    val amount: String,
    @SerialName("paymentRef")
    val paymentRef: String,
    @SerialName("callbackUrl")
    val callbackUrl: String,
    @SerialName("partnerName")
    val partnerName: String,
    @SerialName("RequestRefID")
    val requestRefID: String
)

