package com.chacha.darajacmp.utils

import kotlinx.serialization.Serializable


@Serializable
sealed class DarajaResult<T> {
    @Serializable
    data class Success<T>(val data: T) : DarajaResult<T>()
    
    @Serializable
    data class Error<T>(val error: String, val code: String? = null) : DarajaResult<T>()
}
