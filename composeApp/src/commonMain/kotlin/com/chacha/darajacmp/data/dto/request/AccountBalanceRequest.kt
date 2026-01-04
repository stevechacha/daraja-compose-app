package com.chacha.darajacmp.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Account Balance Models
@Serializable
data class AccountBalanceRequest(
    @SerialName("Initiator")
    val initiator: String,
    @SerialName("SecurityCredential")
    val securityCredential: String,
    @SerialName("CommandID")
    val commandID: String,
    @SerialName("PartyA")
    val partyA: String,
    @SerialName("IdentifierType")
    val identifierType: Int,
    @SerialName("Remarks")
    val remarks: String,
    @SerialName("QueueTimeOutURL")
    val queueTimeOutURL: String,
    @SerialName("ResultURL")
    val resultURL: String
)
