package com.chacha.darajacmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Transaction Status Models
@Serializable
data class TransactionStatusRequest(
    @SerialName("Initiator")
    val initiator: String,
    @SerialName("SecurityCredential")
    val securityCredential: String,
    @SerialName("CommandID")
    val commandID: String,
    @SerialName("TransactionID")
    val transactionID: String,
    @SerialName("PartyA")
    val partyA: String,
    @SerialName("IdentifierType")
    val identifierType: Int,
    @SerialName("ResultURL")
    val resultURL: String,
    @SerialName("QueueTimeOutURL")
    val queueTimeOutURL: String,
    @SerialName("Remarks")
    val remarks: String,
    @SerialName("Occasion")
    val occasion: String
)