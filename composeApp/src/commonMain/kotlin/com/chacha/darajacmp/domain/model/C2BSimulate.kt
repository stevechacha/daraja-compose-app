package com.chacha.darajacmp.domain.model

data class C2BSimulate(
    val conversationID: String,
    val originatorConversationID: String,
    val responseCode: String,
    val responseDescription: String
)

