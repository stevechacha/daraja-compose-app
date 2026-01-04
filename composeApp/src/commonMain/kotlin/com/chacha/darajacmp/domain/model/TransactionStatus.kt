package com.chacha.darajacmp.domain.model

data class TransactionStatus(
    val conversationID: String,
    val originatorConversationID: String,
    val responseCode: String,
    val responseDescription: String
)

