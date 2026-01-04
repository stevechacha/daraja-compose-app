package com.chacha.darajacmp.domain.service

import io.ktor.util.*
import kotlinx.datetime.Clock
import com.chacha.darajacmp.utils.getDarajaTimestamp

/**
 * Domain service for Daraja utility functions.
 * These are pure business logic utilities that don't depend on external frameworks.
 */
interface DarajaService {
    fun getTimestamp(): String
    fun generatePassword(businessShortCode: String, passKey: String): String
}

class DarajaServiceImpl : DarajaService {
    override fun getTimestamp(): String {
        return Clock.System.now().getDarajaTimestamp()
    }
    
    override fun generatePassword(businessShortCode: String, passKey: String): String {
        val timestamp = Clock.System.now().getDarajaTimestamp()
        val password = "$businessShortCode$passKey$timestamp"
        return password.encodeBase64()
    }
}

