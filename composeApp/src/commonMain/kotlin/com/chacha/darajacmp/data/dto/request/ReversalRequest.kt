package com.chacha.darajacmp.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Reversal Models
@Serializable
data class ReversalRequest(
    @SerialName("Initiator")
    val initiator: String,
    @SerialName("SecurityCredential")
    val securityCredential: String,
    @SerialName("CommandID")
    val commandID: String,
    @SerialName("TransactionID")
    val transactionID: String,
    @SerialName("Amount")
    val amount: String,
    @SerialName("ReceiverParty")
    val receiverParty: String,
    @SerialName("RecieverIdentifierType")
    val recieverIdentifierType: String,
    @SerialName("ResultURL")
    val resultURL: String,
    @SerialName("QueueTimeOutURL")
    val queueTimeOutURL: String,
    @SerialName("Remarks")
    val remarks: String,
    @SerialName("Occasion")
    val occasion: String
)
