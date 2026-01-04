package com.chacha.darajacmp.domain.model

data class C2BRegister(
    val conversationID: String,
    val originatorConversationID: String,
    val responseCode: String,
    val responseDescription: String,
    val requestId: String? = null,
    val errorCode: String? = null,
    val errorMessage: String? = null
)

