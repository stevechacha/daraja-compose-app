package com.chacha.darajacmp.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// STK Push Models
@Serializable
data class STKPushRequest(
    @SerialName("BusinessShortCode")
    val businessShortCode: String,
    @SerialName("Password")
    val password: String,
    @SerialName("Timestamp")
    val timestamp: String,
    @SerialName("TransactionType")
    val transactionType: String,
    @SerialName("Amount")
    val amount: Int,
    @SerialName("PartyA")
    val partyA: String,
    @SerialName("PartyB")
    val partyB: String,
    @SerialName("PhoneNumber")
    val phoneNumber: String,
    @SerialName("CallBackURL")
    val callBackURL: String,
    @SerialName("AccountReference")
    val accountReference: String,
    @SerialName("TransactionDesc")
    val transactionDesc: String
)