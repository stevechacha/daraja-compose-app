package com.chacha.darajacmp.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class C2BSimulateResponse(
    @SerialName("ConversationID")
    val conversationID: String,
    @SerialName("OriginatorConversationID")
    val originatorConversationID: String,
    @SerialName("ResponseCode")
    val responseCode: String,
    @SerialName("ResponseDescription")
    val responseDescription: String
)