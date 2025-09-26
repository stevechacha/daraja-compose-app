package com.chacha.darajacmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Bill Manager Models
@Serializable
data class BillManagerRequest(
    @SerialName("shortcode")
    val shortcode: String,
    @SerialName("email")
    val email: String,
    @SerialName("officialContact")
    val officialContact: String,
    @SerialName("sendReminders")
    val sendReminders: String,
    @SerialName("logo")
    val logo: String,
    @SerialName("callbackurl")
    val callbackurl: String
)

