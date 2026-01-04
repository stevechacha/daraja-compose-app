package com.chacha.darajacmp.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DynamicQRResponse(
    @SerialName("ResponseCode")
    val responseCode: String,
    @SerialName("ResponseDescription")
    val responseDescription: String,
    @SerialName("QRCode")
    val qrCode: String
)
