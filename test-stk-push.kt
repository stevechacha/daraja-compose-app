import com.chacha.darajacmp.network.AuthService
import com.chacha.darajacmp.network.DarajaApiService
import com.chacha.darajacmp.config.DarajaConfig

fun main() {
    println("🧪 Testing Daraja STK Push Integration")
    println("=====================================")
    
    // Test 1: Validate Credentials
    println("\n1️⃣ Testing Credential Validation...")
    val validationResult = TestUtils.validateCredentials()
    println(validationResult)
    
    // Test 2: Test Authentication
    println("\n2️⃣ Testing Authentication...")
    runBlocking {
        val authResult = TestUtils.testAuthentication()
        println(authResult)
        
        if (authResult.contains("✅")) {
            // Test 3: Test STK Push
            println("\n3️⃣ Testing STK Push...")
            val stkResult = TestUtils.testSTKPush()
            println(stkResult)
        } else {
            println("❌ Authentication failed, skipping STK Push test")
        }
    }
    
    println("\n🏁 Test Complete!")
}

// Import the TestUtils
import com.chacha.darajacmp.utils.TestUtils
import kotlinx.coroutines.runBlocking
