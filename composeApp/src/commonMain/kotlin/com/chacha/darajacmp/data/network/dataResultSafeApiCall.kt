package com.chacha.darajacmp.data.network

import com.chacha.darajacmp.utils.DarajaResult
import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import io.ktor.serialization.*


internal suspend fun <T: Any> dataResultSafeApiCall(apiCall: suspend () -> T) : DarajaResult<T> =
    try {
        DarajaResult.Success(apiCall.invoke())
    } catch (e: ServerResponseException) {
        val error = parseHttpError(e.response)
        DarajaResult.Error(error)
    } catch (e: RedirectResponseException) {
        val error = parseHttpError(e.response)
        DarajaResult.Error(error)
    } catch (e: Exception) {
        val error = parseSimpleError(e)
        DarajaResult.Error(error)
    }


internal suspend fun parseHttpError(response: HttpResponse): String {
    val statusCode = response.status.value
    val errorBody = try {
        response.bodyAsText()
    } catch (e: Exception) {
        "Unable to read error response"
    }
    
    return when (statusCode) {
        400 -> parseDarajaError(errorBody)
        401 -> "Authentication failed - Please check your credentials: $errorBody"
        403 -> "Access denied - Insufficient permissions: $errorBody"
        404 -> "Resource not found: $errorBody"
        408 -> "Request timeout - Please try again: $errorBody"
        429 -> "Too many requests - Rate limit exceeded: $errorBody"
        500 -> "Server error - Please try again later: $errorBody"
        502 -> "Bad gateway - Service temporarily unavailable: $errorBody"
        503 -> "Service unavailable - Please try again later: $errorBody"
        else -> "HTTP Error $statusCode: $errorBody"
    }
}

internal fun parseDarajaError(errorBody: String): String {
    return when {
        // Common Daraja API Error Codes
        errorBody.contains("400.002.02") -> {
            when {
                errorBody.contains("Invalid InitiatorName") -> 
                    "Invalid Initiator Name - Please check your credentials"
                errorBody.contains("Invalid BusinessShortCode") -> 
                    "Invalid Business Short Code - Please check your short code"
                errorBody.contains("Invalid PhoneNumber") -> 
                    "Invalid Phone Number - Use format 2547xxxxxxxx (without +)"
                errorBody.contains("Invalid Amount") -> 
                    "Invalid Amount - Amount must be greater than 0"
                else -> "Bad Request - Invalid parameters: $errorBody"
            }
        }
        
        errorBody.contains("400.001.01") -> 
            "Invalid Request - Missing required parameters"
        
        errorBody.contains("400.001.02") -> 
            "Invalid Request - Invalid parameter format"
        
        errorBody.contains("400.001.03") -> 
            "Invalid Request - Parameter value out of range"
        
        errorBody.contains("400.001.04") -> 
            "Invalid Request - Duplicate request"
        
        errorBody.contains("400.001.05") -> 
            "Invalid Request - Invalid transaction reference"
        
        errorBody.contains("400.001.06") -> 
            "Invalid Request - Invalid account reference"
        
        errorBody.contains("400.001.07") -> 
            "Invalid Request - Invalid party information"
        
        errorBody.contains("400.001.08") -> 
            "Invalid Request - Invalid remarks"
        
        errorBody.contains("400.001.09") -> 
            "Invalid Request - Invalid occasion"
        
        errorBody.contains("400.001.10") -> 
            "Invalid Request - Invalid queue timeout URL"
        
        errorBody.contains("400.001.11") -> 
            "Invalid Request - Invalid result URL"
        
        errorBody.contains("400.001.12") -> 
            "Invalid Request - Invalid callback URL"
        
        errorBody.contains("400.001.13") -> 
            "Invalid Request - Invalid command ID"
        
        errorBody.contains("400.001.14") -> 
            "Invalid Request - Invalid security credential"
        
        errorBody.contains("400.001.15") -> 
            "Invalid Request - Invalid initiator"
        
        errorBody.contains("400.001.16") -> 
            "Invalid Request - Invalid party A"
        
        errorBody.contains("400.001.17") -> 
            "Invalid Request - Invalid party B"
        
        errorBody.contains("400.001.18") -> 
            "Invalid Request - Invalid amount"
        
        errorBody.contains("400.001.19") -> 
            "Invalid Request - Invalid transaction ID"
        
        errorBody.contains("400.001.20") -> 
            "Invalid Request - Invalid identifier type"
        
        errorBody.contains("400.001.21") -> 
            "Invalid Request - Invalid short code"
        
        errorBody.contains("400.001.22") -> 
            "Invalid Request - Invalid till number"
        
        errorBody.contains("400.001.23") -> 
            "Invalid Request - Invalid paybill number"
        
        errorBody.contains("400.001.24") -> 
            "Invalid Request - Invalid account number"
        
        errorBody.contains("400.001.25") -> 
            "Invalid Request - Invalid organization"
        
        errorBody.contains("400.001.26") -> 
            "Invalid Request - Invalid customer"
        
        errorBody.contains("400.001.27") -> 
            "Invalid Request - Invalid agent"
        
        errorBody.contains("400.001.28") -> 
            "Invalid Request - Invalid merchant"
        
        errorBody.contains("400.001.29") -> 
            "Invalid Request - Invalid vendor"
        
        errorBody.contains("400.001.30") -> 
            "Invalid Request - Invalid supplier"
        
        // Business Logic Errors
        errorBody.contains("400.002.01") -> 
            "Business Logic Error - Invalid business short code"
        
        errorBody.contains("400.002.03") -> 
            "Business Logic Error - Invalid phone number format"
        
        errorBody.contains("400.002.04") -> 
            "Business Logic Error - Invalid amount"
        
        errorBody.contains("400.002.05") -> 
            "Business Logic Error - Invalid transaction reference"
        
        errorBody.contains("400.002.06") -> 
            "Business Logic Error - Invalid account reference"
        
        errorBody.contains("400.002.07") -> 
            "Business Logic Error - Invalid party information"
        
        errorBody.contains("400.002.08") -> 
            "Business Logic Error - Invalid remarks"
        
        errorBody.contains("400.002.09") -> 
            "Business Logic Error - Invalid occasion"
        
        errorBody.contains("400.002.10") -> 
            "Business Logic Error - Invalid queue timeout URL"
        
        errorBody.contains("400.002.11") -> 
            "Business Logic Error - Invalid result URL"
        
        errorBody.contains("400.002.12") -> 
            "Business Logic Error - Invalid callback URL"
        
        errorBody.contains("400.002.13") -> 
            "Business Logic Error - Invalid command ID"
        
        errorBody.contains("400.002.14") -> 
            "Business Logic Error - Invalid security credential"
        
        errorBody.contains("400.002.15") -> 
            "Business Logic Error - Invalid initiator"
        
        errorBody.contains("400.002.16") -> 
            "Business Logic Error - Invalid party A"
        
        errorBody.contains("400.002.17") -> 
            "Business Logic Error - Invalid party B"
        
        errorBody.contains("400.002.18") -> 
            "Business Logic Error - Invalid amount"
        
        errorBody.contains("400.002.19") -> 
            "Business Logic Error - Invalid transaction ID"
        
        errorBody.contains("400.002.20") -> 
            "Business Logic Error - Invalid identifier type"
        
        errorBody.contains("400.002.21") -> 
            "Business Logic Error - Invalid short code"
        
        errorBody.contains("400.002.22") -> 
            "Business Logic Error - Invalid till number"
        
        errorBody.contains("400.002.23") -> 
            "Business Logic Error - Invalid paybill number"
        
        errorBody.contains("400.002.24") -> 
            "Business Logic Error - Invalid account number"
        
        errorBody.contains("400.002.25") -> 
            "Business Logic Error - Invalid organization"
        
        errorBody.contains("400.002.26") -> 
            "Business Logic Error - Invalid customer"
        
        errorBody.contains("400.002.27") -> 
            "Business Logic Error - Invalid agent"
        
        errorBody.contains("400.002.28") -> 
            "Business Logic Error - Invalid merchant"
        
        errorBody.contains("400.002.29") -> 
            "Business Logic Error - Invalid vendor"
        
        errorBody.contains("400.002.30") -> 
            "Business Logic Error - Invalid supplier"
        
        // System Errors
        errorBody.contains("500.001.01") -> 
            "System Error - Internal server error"
        
        errorBody.contains("500.001.02") -> 
            "System Error - Service temporarily unavailable"
        
        errorBody.contains("500.001.03") -> 
            "System Error - Database connection error"
        
        errorBody.contains("500.001.04") -> 
            "System Error - External service error"
        
        errorBody.contains("500.001.05") -> 
            "System Error - Configuration error"
        
        errorBody.contains("500.001.06") -> 
            "System Error - Authentication service error"
        
        errorBody.contains("500.001.07") -> 
            "System Error - Authorization service error"
        
        errorBody.contains("500.001.08") -> 
            "System Error - Validation service error"
        
        errorBody.contains("500.001.09") -> 
            "System Error - Business logic service error"
        
        errorBody.contains("500.001.10") -> 
            "System Error - Notification service error"
        
        // Generic fallback
        else -> "Bad Request - Invalid parameters: $errorBody"
    }
}

internal fun parseSimpleError(exception: Exception): String {
    return when {
        // JSON/Serialization errors
        exception is JsonConvertException -> 
            "Failed to parse response - Invalid data format"
        
        exception.message?.contains("serialization", ignoreCase = true) == true -> 
            "Failed to parse response - Invalid data format"
        
        // Network errors
        exception.message?.contains("timeout", ignoreCase = true) == true -> 
            "Connection timeout - Please check your internet connection"
        
        exception.message?.contains("network", ignoreCase = true) == true -> 
            "Network error - Please check your internet connection"
        
        exception.message?.contains("connection", ignoreCase = true) == true -> 
            "Connection failed - Please check your internet connection"
        
        // HTTP errors
        exception.message?.contains("401", ignoreCase = true) == true -> 
            "Authentication failed - Please check your credentials"
        
        exception.message?.contains("403", ignoreCase = true) == true -> 
            "Access denied - Insufficient permissions"
        
        exception.message?.contains("404", ignoreCase = true) == true -> 
            "Resource not found"
        
        exception.message?.contains("500", ignoreCase = true) == true -> 
            "Server error - Please try again later"
        
        // M-Pesa specific errors
        exception.message?.contains("Invalid InitiatorName", ignoreCase = true) == true -> 
            "Invalid initiator name - Please check your credentials"
        
        exception.message?.contains("Insufficient balance", ignoreCase = true) == true -> 
            "Insufficient balance for this transaction"
        
        exception.message?.contains("Invalid phone number", ignoreCase = true) == true -> 
            "Invalid phone number format"
        
        // Generic fallback
        else -> exception.message ?: "An unexpected error occurred"
    }
}

// Simple exception class for M-Pesa errors
class DarajaException(
    override val message: String,
    val errorCode: String? = null,
    val statusCode: Int? = null
) : Exception(message) {
    
    companion object {
        fun networkError(message: String = "Network error - Please check your internet connection") = 
            DarajaException(message, "NETWORK_ERROR")
        
        fun authenticationError(message: String = "Authentication failed - Please check your credentials") = 
            DarajaException(message, "AUTH_ERROR")
        
        fun serverError(message: String = "Server error - Please try again later") = 
            DarajaException(message, "SERVER_ERROR")
        
        fun clientError(message: String = "Invalid request - Please check your input") = 
            DarajaException(message, "CLIENT_ERROR")
        
        fun mpesaError(message: String, errorCode: String? = null) = 
            DarajaException(message, errorCode)
    }
    }