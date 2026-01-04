package com.chacha.darajacmp.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class STKQueryResponse(
    @SerialName("ResponseCode")
    val responseCode: String? = null,
    @SerialName("ResponseDescription")
    val responseDescription: String? = null,
    @SerialName("MerchantRequestID")
    val merchantRequestID: String? = null,
    @SerialName("CheckoutRequestID")
    val checkoutRequestID: String? = null,
    @SerialName("ResultCode")
    val resultCode: Int? = null,
    @SerialName("ResultDesc")
    val resultDesc: String? = null
)