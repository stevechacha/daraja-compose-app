package com.chacha.darajacmp.domain.model

data class STKPush(
    val merchantRequestID: String,
    val checkoutRequestID: String,
    val responseCode: String,
    val responseDescription: String,
    val customerMessage: String
)

