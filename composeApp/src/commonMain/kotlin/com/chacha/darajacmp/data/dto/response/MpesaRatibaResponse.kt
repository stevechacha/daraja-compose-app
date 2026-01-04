package com.chacha.darajacmp.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MpesaRatibaResponse(
    @SerialName("ConversationID")
    val conversationID: String? = null,
    @SerialName("OriginatorConversationID")
    val originatorConversationID: String? = null,
    @SerialName("ResponseCode")
    val responseCode: String? = null,
    @SerialName("ResponseDescription")
    val responseDescription: String? = null,
    @SerialName("requestId")
    val requestId: String? = null,
    @SerialName("errorCode")
    val errorCode: String? = null,
    @SerialName("errorMessage")
    val errorMessage: String? = null
)


