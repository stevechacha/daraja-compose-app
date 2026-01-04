package com.chacha.darajacmp.domain.model

data class BillManager(
    val conversationID: String? = null,
    val originatorConversationID: String? = null,
    val responseCode: String? = null,
    val responseDescription: String? = null,
    val requestId: String? = null,
    val errorCode: String? = null,
    val errorMessage: String? = null
)

