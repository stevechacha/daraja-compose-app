import com.chacha.darajacmp.network.AuthService
import com.chacha.darajacmp.network.DarajaApiService
import com.chacha.darajacmp.config.DarajaConfig
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("🔍 Debugging STK Push Issues")
    println("============================")
    
    // Test credentials
    val clientId = DarajaConfig.DarajaCredentials.CLIENT_ID
    val clientSecret = DarajaConfig.DarajaCredentials.CLIENT_SECRET
    val businessShortCode = DarajaConfig.DarajaCredentials.BUSINESS_SHORT_CODE
    val passKey = DarajaConfig.DarajaCredentials.PASS_KEY
    val testPhone = DarajaConfig.DarajaCredentials.TEST_PHONE_NUMBER
    
    println("\n📋 Test Configuration:")
    println("   Client ID: ${clientId.take(10)}...")
    println("   Business Short Code: $businessShortCode")
    println("   Test Phone: $testPhone")
    println("   Pass Key: ${passKey.take(10)}...")
    
    // Test 1: Authentication
    println("\n1️⃣ Testing Authentication...")
    val authService = AuthService()
    val authResult = authService.authenticate(clientId, clientSecret)
    
    when (authResult) {
        is com.chacha.darajacmp.models.DarajaResult.Success -> {
            println("✅ Authentication successful!")
            
            // Test 2: STK Push
            println("\n2️⃣ Testing STK Push...")
            val apiService = DarajaApiService(authService)
            
            val timestamp = apiService.getTimestamp()
            val password = apiService.generatePassword(businessShortCode, passKey)
            
            println("   Timestamp: $timestamp")
            println("   Password: ${password.take(20)}...")
            
            val stkResult = apiService.stkPush(
                businessShortCode = businessShortCode,
                password = password,
                timestamp = timestamp,
                amount = 100,
                phoneNumber = testPhone,
                callBackURL = "https://your-callback-url.com",
                accountReference = "Test Payment",
                transactionDesc = "Test Transaction",
                clientId = clientId,
                clientSecret = clientSecret
            )
            
            when (stkResult) {
                is com.chacha.darajacmp.models.DarajaResult.Success -> {
                    println("✅ STK Push successful!")
                    println("   Merchant Request ID: ${stkResult.data.merchantRequestID}")
                    println("   Checkout Request ID: ${stkResult.data.checkoutRequestID}")
                    println("   Response Code: ${stkResult.data.responseCode}")
                    println("   Response Description: ${stkResult.data.responseDescription}")
                    println("   Customer Message: ${stkResult.data.customerMessage}")
                }
                is com.chacha.darajacmp.models.DarajaResult.Error -> {
                    println("❌ STK Push failed: ${stkResult.error}")
                }
            }
        }
        is com.chacha.darajacmp.models.DarajaResult.Error -> {
            println("❌ Authentication failed: ${authResult.error}")
        }
    }
    
    println("\n🏁 Debug complete!")
}
