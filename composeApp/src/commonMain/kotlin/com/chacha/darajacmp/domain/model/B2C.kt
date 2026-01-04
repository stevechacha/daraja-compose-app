package com.chacha.darajacmp.domain.model

data class B2C(
    val conversationID: String,
    val originatorConversationID: String,
    val responseCode: String,
    val responseDescription: String
)

