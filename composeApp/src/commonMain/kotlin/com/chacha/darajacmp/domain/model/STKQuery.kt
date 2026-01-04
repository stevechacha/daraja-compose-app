package com.chacha.darajacmp.domain.model

data class STKQuery(
    val responseCode: String,
    val responseDescription: String,
    val merchantRequestID: String? = null,
    val checkoutRequestID: String? = null,
    val resultCode: Int? = null,
    val resultDesc: String? = null
)

