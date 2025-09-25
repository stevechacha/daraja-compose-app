package com.chacha.darajacmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// B2C Account Top Up Models
@Serializable
data class B2CAccountTopUpRequest(
    @SerialName("Initiator")
    val initiator: String,
    @SerialName("SecurityCredential")
    val securityCredential: String,
    @SerialName("CommandID")
    val commandID: String,
    @SerialName("Amount")
    val amount: Int,
    @SerialName("PartyA")
    val partyA: String,
    @SerialName("PartyB")
    val partyB: String,
    @SerialName("Remarks")
    val remarks: String,
    @SerialName("QueueTimeOutURL")
    val queueTimeOutURL: String,
    @SerialName("ResultURL")
    val resultURL: String,
    @SerialName("Occasion")
    val occasion: String
)
