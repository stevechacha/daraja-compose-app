package com.chacha.darajacmp.utils

import com.chacha.darajacmp.utils.DarajaConfig
import com.chacha.darajacmp.network.AuthService
import com.chacha.darajacmp.network.DarajaApiService
import kotlinx.datetime.Clock

/**
 * Test utilities for Daraja API integration
 */
object TestUtils {
    
    /**
     * Test authentication with your credentials
     */
    suspend fun testAuthentication(): String {
        return try {
            val authService = AuthService()
            val result = authService.authenticate(
                clientId = DarajaConfig.DarajaCredentials.CLIENT_ID,
                clientSecret = DarajaConfig.DarajaCredentials.CLIENT_SECRET
            )
            
            when (result) {
                is DarajaResult.Success -> {
                    "✅ Authentication successful! Access token received."
                }
                is DarajaResult.Error -> {
                    "❌ Authentication failed: ${result.error}"
                }
            }
        } catch (e: Exception) {
            "❌ Authentication error: ${e.message}"
        }
    }
    
    /**
     * Test STK Push with sample data
     */
//    suspend fun testSTKPush(): String {
//        return try {
//            val authService = AuthService()
//            val apiService = DarajaApiService(authService)
//            val timestamp = Clock.System.now().getDarajaTimestamp()
//
//
//            val result = apiService.stkPush(
//                businessShortCode = DarajaConfig.DarajaCredentials.BUSINESS_SHORT_CODE,
//                password = apiService.generatePassword(
//                    DarajaConfig.DarajaCredentials.BUSINESS_SHORT_CODE,
//                    DarajaConfig.DarajaCredentials.PASS_KEY
//                ),
//                timestamp = timestamp,
//                amount = 100,
//                phoneNumber = DarajaConfig.DarajaCredentials.TEST_PHONE_NUMBER,
//                callBackURL = "https://your-callback-url.com",
//                accountReference = "Test Payment",
//                transactionDesc = "Test Transaction",
//                clientId = DarajaConfig.DarajaCredentials.CLIENT_ID,
//                clientSecret = DarajaConfig.DarajaCredentials.CLIENT_SECRET
//            )
//
//            when (result) {
//                is DarajaResult.Success -> {
//                    "✅ STK Push initiated successfully!\n" +
//                    "Merchant Request ID: ${result.data.merchantRequestID}\n" +
//                    "Checkout Request ID: ${result.data.checkoutRequestID}\n" +
//                    "Response: ${result.data.responseDescription}"
//                }
//                is DarajaResult.Error -> {
//                    "❌ STK Push failed: ${result.error}"
//                }
//            }
//        } catch (e: Exception) {
//            "❌ STK Push error: ${e.message}"
//        }
//    }
    
    /**
     * Validate your credentials format
     */
    fun validateCredentials(): String {
        val clientId = DarajaConfig.DarajaCredentials.CLIENT_ID
        val clientSecret = DarajaConfig.DarajaCredentials.CLIENT_SECRET
        val businessShortCode = DarajaConfig.DarajaCredentials.BUSINESS_SHORT_CODE
        val passKey = DarajaConfig.DarajaCredentials.PASS_KEY
        
        val issues = mutableListOf<String>()
        
        if (clientId.isEmpty()) {
            issues.add("Client ID is empty")
        }
        
        if (clientSecret.isEmpty()) {
            issues.add("Client Secret is empty")
        }
        
        if (businessShortCode.isEmpty()) {
            issues.add("Business Short Code is empty")
        }
        
        if (passKey.isEmpty()) {
            issues.add("Pass Key is empty")
        }
        
        if (clientId.length < 20) {
            issues.add("Client ID seems too short (${clientId.length} characters)")
        }
        
        if (clientSecret.length < 50) {
            issues.add("Client Secret seems too short (${clientSecret.length} characters)")
        }
        
        if (businessShortCode != "174379" && businessShortCode.length != 6) {
            issues.add("Business Short Code should be 6 digits (current: $businessShortCode)")
        }
        
        return if (issues.isEmpty()) {
            "✅ All credentials appear to be properly formatted!"
        } else {
            "⚠️ Credential validation issues:\n" + issues.joinToString("\n")
        }
    }
    
    /**
     * Get test information
     */
    fun getTestInfo(): String {
        return """
            🔧 Daraja API Test Information
            
            Environment: Sandbox
            Client ID: ${DarajaConfig.DarajaCredentials.CLIENT_ID.take(10)}...
            Business Short Code: ${DarajaConfig.DarajaCredentials.BUSINESS_SHORT_CODE}
            Test Phone: ${DarajaConfig.DarajaCredentials.TEST_PHONE_NUMBER}
            
            📱 Test Phone Numbers (Sandbox):
            • 254708374149 (Default test number)
            • 254711000000 - 254711000999 (Range of test numbers)
            
            💰 Test Amount Limits:
            • Minimum: 1 KES
            • Maximum: 70,000 KES
            
            🌐 Sandbox URLs:
            • Auth: ${DarajaConfig.SANDBOX_AUTH_URL}
            • STK Push: ${DarajaConfig.SANDBOX_STK_PUSH_URL}
            
            ⚠️ Important Notes:
            • This is SANDBOX environment - no real money
            • Use test phone numbers only
            • Callback URLs need to be publicly accessible
            • For production, update URLs and get approval from Safaricom
        """.trimIndent()
    }
}
