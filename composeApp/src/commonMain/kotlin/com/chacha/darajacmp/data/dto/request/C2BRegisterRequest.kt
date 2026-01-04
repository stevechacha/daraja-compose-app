package com.chacha.darajacmp.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// C2B Register URL Models
@Serializable
data class C2BRegisterRequest(
    @SerialName("ShortCode")
    val shortCode: String,
    @SerialName("ResponseType")
    val responseType: String,
    @SerialName("ConfirmationURL")
    val confirmationURL: String,
    @SerialName("ValidationURL")
    val validationURL: String
)