package com.chacha.darajacmp.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// C2B Simulate Models
@Serializable
data class C2BSimulateRequest(
    @SerialName("ShortCode")
    val shortCode: String,
    @SerialName("CommandID")
    val commandID: String,
    @SerialName("Amount")
    val amount: Int,
    @SerialName("Msisdn")
    val msisdn: String,
    @SerialName("BillRefNumber")
    val billRefNumber: String
)
