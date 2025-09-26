package com.chacha.darajacmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// M-Pesa Ratiba Models
@Serializable
data class MpesaRatibaRequest(
    @SerialName("StandingOrderName")
    val standingOrderName: String,
    @SerialName("StartDate")
    val startDate: String,
    @SerialName("EndDate")
    val endDate: String,
    @SerialName("BusinessShortCode")
    val businessShortCode: String,
    @SerialName("TransactionType")
    val transactionType: String,
    @SerialName("ReceiverPartyIdentifierType")
    val receiverPartyIdentifierType: String,
    @SerialName("Amount")
    val amount: String,
    @SerialName("PartyA")
    val partyA: String,
    @SerialName("CallBackURL")
    val callBackURL: String,
    @SerialName("AccountReference")
    val accountReference: String,
    @SerialName("TransactionDesc")
    val transactionDesc: String,
    @SerialName("Frequency")
    val frequency: String
)

