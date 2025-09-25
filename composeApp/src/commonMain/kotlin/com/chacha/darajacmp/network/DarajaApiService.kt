package com.chacha.darajacmp.network

import com.chacha.darajacmp.models.*
import com.chacha.darajacmp.network.response.*
import com.chacha.darajacmp.utils.DarajaResult
import com.chacha.darajacmp.utils.getDarajaTimestamp
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json

class DarajaApiService(private val authService: AuthService) {
    
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                encodeDefaults = true
                coerceInputValues = true
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
        install(DefaultRequest) {
            contentType(ContentType.Application.Json)
        }
    }

    
    // STK Push - Initiate payment
    suspend fun stkPush(
        businessShortCode: String,
        password: String,
        timestamp: String,
        amount: Int,
        phoneNumber: String,
        callBackURL: String,
        accountReference: String,
        transactionDesc: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<STKPushResponse> {
        return try {
            println("🔐 Getting access token...")
            val token = authService.getValidToken(clientId, clientSecret)
                ?: return DarajaResult.Error("Failed to get access token")
            
            println("✅ Access token received: ${token.take(20)}...")
            
            val request = STKPushRequest(
                businessShortCode = businessShortCode,
                password = password,
                timestamp = timestamp,
                transactionType = "CustomerPayBillOnline",
                amount = amount,
                partyA = phoneNumber,
                partyB = businessShortCode,
                phoneNumber = phoneNumber,
                callBackURL = callBackURL,
                accountReference = accountReference,
                transactionDesc = transactionDesc
            )
            
            println("📱 Sending STK Push request...")
            println("   Amount: $amount KES")
            println("   Phone: $phoneNumber")
            println("   Business Code: $businessShortCode")
            
            val response = client.post("https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }
            
            val responseText = response.body<String>()
            println("📡 STK Push raw response: $responseText")
            
            val json = Json { 
                ignoreUnknownKeys = true
                isLenient = true
            }
            
            val stkResponse = json.decodeFromString<STKPushResponse>(responseText)
            
            println("✅ STK Push response received")
            DarajaResult.Success(stkResponse)
        } catch (e: Exception) {
            println("❌ STK Push error: ${e.message}")
            e.printStackTrace()
            DarajaResult.Error("STK Push failed: ${e.message}")
        }
    }
    
    // STK Query - Check payment status
    suspend fun stkQuery(
        businessShortCode: String,
        password: String,
        timestamp: String,
        checkoutRequestID: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<STKQueryResponse> {
        return try {
            println("🔐 Getting access token for STK Query...")
            val token = authService.getValidToken(clientId, clientSecret)
                ?: return DarajaResult.Error("Failed to get access token")

            println("✅ Access token received for STK Query: ${token.take(20)}...")

            val request = STKQueryRequest(
                businessShortCode = businessShortCode,
                password = password,
                timestamp = timestamp,
                checkoutRequestID = checkoutRequestID
            )

            println("🔍 Querying STK Push status...")
            println("   Business Code: $businessShortCode")
            println("   Checkout Request ID: $checkoutRequestID")
            println("   Timestamp: $timestamp")

            val response = client.post("https://sandbox.safaricom.co.ke/mpesa/stkpushquery/v1/query") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }
            
            val responseText = response.body<String>()
            println("📡 STK Query raw response: $responseText")
            
            if (responseText.isBlank()) {
                throw Exception("Empty response from STK Query API")
            }
            
            val json = Json { 
                ignoreUnknownKeys = true
                isLenient = true
                coerceInputValues = true
            }
            
            val queryResponse = json.decodeFromString<STKQueryResponse>(responseText)
            
            println("✅ STK Query response received")
            DarajaResult.Success(queryResponse)
        } catch (e: Exception) {
            println("❌ STK Query error: ${e.message}")
            e.printStackTrace()
            DarajaResult.Error("STK Query failed: ${e.message}")
        }
    }
    
    // C2B Register URL
    suspend fun c2bRegisterURL(
        shortCode: String,
        responseType: String,
        confirmationURL: String,
        validationURL: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<C2BRegisterResponse> {
        return try {
            println("🔐 Getting access token for C2B Register...")
            val token = authService.getValidToken(clientId, clientSecret)
                ?: return DarajaResult.Error("Failed to get access token")

            println("✅ Access token received for C2B Register: ${token.take(20)}...")

            val request = C2BRegisterRequest(
                shortCode = shortCode,
                responseType = responseType,
                confirmationURL = confirmationURL,
                validationURL = validationURL
            )

            println("🔗 Registering C2B URLs...")
            println("   Short Code: $shortCode")
            println("   Response Type: $responseType")
            println("   Confirmation URL: $confirmationURL")
            println("   Validation URL: $validationURL")

            val response = client.post("https://sandbox.safaricom.co.ke/mpesa/c2b/v1/registerurl") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }
            
            val responseText = response.body<String>()
            println("📡 C2B Register raw response: $responseText")
            
            val json = Json { 
                ignoreUnknownKeys = true
                isLenient = true
            }
            
            val registerResponse = json.decodeFromString<C2BRegisterResponse>(responseText)
            
            println("✅ C2B Register response received")
            DarajaResult.Success(registerResponse)
        } catch (e: Exception) {
            println("❌ C2B Register error: ${e.message}")
            e.printStackTrace()
            DarajaResult.Error("C2B Register URL failed: ${e.message}")
        }
    }
    
    // C2B Simulate Transaction
    suspend fun c2bSimulate(
        shortCode: String,
        commandID: String,
        amount: Int,
        msisdn: String,
        billRefNumber: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<C2BSimulateResponse> {
        return try {
            val token = authService.getValidToken(clientId, clientSecret)
                ?: return DarajaResult.Error("Failed to get access token")
            
            val request = C2BSimulateRequest(
                shortCode = shortCode,
                commandID = commandID,
                amount = amount,
                msisdn = msisdn,
                billRefNumber = billRefNumber
            )
            
            val response: C2BSimulateResponse = client.post("https://sandbox.safaricom.co.ke/mpesa/c2b/v1/simulate") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }.body()
            
            DarajaResult.Success(response)
        } catch (e: Exception) {
            DarajaResult.Error("C2B Simulate failed: ${e.message}")
        }
    }
    
    // B2C - Business to Customer
    suspend fun b2c(
        initiatorName: String,
        securityCredential: String,
        commandID: String,
        amount: Int,
        partyA: String,
        partyB: String,
        remarks: String,
        queueTimeOutURL: String,
        resultURL: String,
        occasion: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<B2CResponse> {
        return try {
            val token = authService.getValidToken(clientId, clientSecret)
                ?: return DarajaResult.Error("Failed to get access token")
            
            val request = B2CRequest(
                initiatorName = initiatorName,
                securityCredential = securityCredential,
                commandID = commandID,
                amount = amount,
                partyA = partyA,
                partyB = partyB,
                remarks = remarks,
                queueTimeOutURL = queueTimeOutURL,
                resultURL = resultURL,
                occasion = occasion
            )
            
            val response: B2CResponse = client.post("https://sandbox.safaricom.co.ke/mpesa/b2c/v1/paymentrequest") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }.body()
            
            DarajaResult.Success(response)
        } catch (e: Exception) {
            DarajaResult.Error("B2C failed: ${e.message}")
        }
    }
    
    // Account Balance
    suspend fun accountBalance(
        initiator: String,
        securityCredential: String,
        commandID: String,
        partyA: String,
        identifierType: Int,
        remarks: String,
        queueTimeOutURL: String,
        resultURL: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<AccountBalanceResponse> {
        return try {
            println("🔐 Getting access token for Account Balance...")
            val token = authService.getValidToken(clientId, clientSecret)
                ?: return DarajaResult.Error("Failed to get access token")

            println("✅ Access token received for Account Balance: ${token.take(20)}...")

            val request = AccountBalanceRequest(
                initiator = initiator,
                securityCredential = securityCredential,
                commandID = commandID,
                partyA = partyA,
                identifierType = identifierType,
                remarks = remarks,
                queueTimeOutURL = queueTimeOutURL,
                resultURL = resultURL
            )

            println("💰 Querying account balance...")
            println("   Party A: $partyA")
            println("   Command ID: $commandID")
            println("   Initiator: $initiator")
            println("   Identifier Type: $identifierType")

            val response = client.post("https://sandbox.safaricom.co.ke/mpesa/accountbalance/v1/query") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }
            
            val responseText = response.body<String>()
            println("📡 Account Balance raw response: $responseText")
            
            val json = Json { 
                ignoreUnknownKeys = true
                isLenient = true
            }
            
            val balanceResponse = json.decodeFromString<AccountBalanceResponse>(responseText)
            
            println("✅ Account Balance response received")
            DarajaResult.Success(balanceResponse)
        } catch (e: Exception) {
            println("❌ Account Balance error: ${e.message}")
            e.printStackTrace()
            DarajaResult.Error("Account Balance failed: ${e.message}")
        }
    }
    
    // Transaction Status
    suspend fun transactionStatus(
        initiator: String,
        securityCredential: String,
        commandID: String,
        transactionID: String,
        partyA: String,
        identifierType: Int,
        resultURL: String,
        queueTimeOutURL: String,
        remarks: String,
        occasion: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<TransactionStatusResponse> {
        return try {
            println("🔐 Getting access token for Transaction Status...")
            val token = authService.getValidToken(clientId, clientSecret)
                ?: return DarajaResult.Error("Failed to get access token")

            println("✅ Access token received for Transaction Status: ${token.take(20)}...")

            val request = TransactionStatusRequest(
                initiator = initiator,
                securityCredential = securityCredential,
                commandID = commandID,
                transactionID = transactionID,
                partyA = partyA,
                identifierType = identifierType,
                resultURL = resultURL,
                queueTimeOutURL = queueTimeOutURL,
                remarks = remarks,
                occasion = occasion
            )

            println("🔍 Querying transaction status...")
            println("   Transaction ID: $transactionID")
            println("   Party A: $partyA")
            println("   Command ID: $commandID")
            println("   Initiator: $initiator")

            val response = client.post("https://sandbox.safaricom.co.ke/mpesa/transactionstatus/v1/query") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }
            
            val responseText = response.body<String>()
            println("📡 Transaction Status raw response: $responseText")
            
            val json = Json { 
                ignoreUnknownKeys = true
                isLenient = true
            }
            
            val statusResponse = json.decodeFromString<TransactionStatusResponse>(responseText)
            
            println("✅ Transaction Status response received")
            DarajaResult.Success(statusResponse)
        } catch (e: Exception) {
            println("❌ Transaction Status error: ${e.message}")
            e.printStackTrace()
            DarajaResult.Error("Transaction Status failed: ${e.message}")
        }
    }
    
    // Reversal
    suspend fun reversal(
        initiator: String,
        securityCredential: String,
        commandID: String,
        transactionID: String,
        amount: Int,
        receiverParty: String,
        recieverIdentifierType: Int,
        resultURL: String,
        queueTimeOutURL: String,
        remarks: String,
        occasion: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<ReversalResponse> {
        return try {
            println("🔐 Getting access token for Reversal...")
            val token = authService.getValidToken(clientId, clientSecret)
                ?: return DarajaResult.Error("Failed to get access token")

            println("✅ Access token received for Reversal: ${token.take(20)}...")

            val request = ReversalRequest(
                initiator = initiator,
                securityCredential = securityCredential,
                commandID = commandID,
                transactionID = transactionID,
                amount = amount,
                receiverParty = receiverParty,
                recieverIdentifierType = recieverIdentifierType,
                resultURL = resultURL,
                queueTimeOutURL = queueTimeOutURL,
                remarks = remarks,
                occasion = occasion
            )

            println("🔄 Initiating transaction reversal...")
            println("   Transaction ID: $transactionID")
            println("   Amount: $amount KES")
            println("   Receiver Party: $receiverParty")
            println("   Command ID: $commandID")
            println("   Initiator: $initiator")

            val response = client.post("https://sandbox.safaricom.co.ke/mpesa/reversal/v1/request") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }
            
            val responseText = response.body<String>()
            println("📡 Reversal raw response: $responseText")
            
            val json = Json { 
                ignoreUnknownKeys = true
                isLenient = true
            }
            
            val reversalResponse = json.decodeFromString<ReversalResponse>(responseText)
            
            println("✅ Reversal response received")
            DarajaResult.Success(reversalResponse)
        } catch (e: Exception) {
            println("❌ Reversal error: ${e.message}")
            e.printStackTrace()
            DarajaResult.Error("Reversal failed: ${e.message}")
        }
    }
    
    // Tax Remittance
    suspend fun taxRemittance(
        initiator: String,
        securityCredential: String,
        commandID: String,
        amount: Int,
        partyA: String,
        partyB: String,
        remarks: String,
        queueTimeOutURL: String,
        resultURL: String,
        occasion: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<TaxRemittanceResponse> {
        return try {
            println("🔐 Getting access token for Tax Remittance...")
            val token = authService.getValidToken(clientId, clientSecret)
                ?: return DarajaResult.Error("Failed to get access token")

            println("✅ Access token received for Tax Remittance: ${token.take(20)}...")

            val request = TaxRemittanceRequest(
                initiator = initiator,
                securityCredential = securityCredential,
                commandID = commandID,
                amount = amount,
                partyA = partyA,
                partyB = partyB,
                remarks = remarks,
                queueTimeOutURL = queueTimeOutURL,
                resultURL = resultURL,
                occasion = occasion
            )

            println("💰 Initiating tax remittance...")
            println("   Amount: $amount KES")
            println("   Party A: $partyA")
            println("   Party B: $partyB")
            println("   Command ID: $commandID")
            println("   Initiator: $initiator")

            val response = client.post("https://sandbox.safaricom.co.ke/mpesa/b2c/v1/paymentrequest") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }
            
            val responseText = response.body<String>()
            println("📡 Tax Remittance raw response: $responseText")
            
            val json = Json { 
                ignoreUnknownKeys = true
                isLenient = true
            }
            
            val taxResponse = json.decodeFromString<TaxRemittanceResponse>(responseText)
            
            println("✅ Tax Remittance response received")
            DarajaResult.Success(taxResponse)
        } catch (e: Exception) {
            println("❌ Tax Remittance error: ${e.message}")
            e.printStackTrace()
            DarajaResult.Error("Tax Remittance failed: ${e.message}")
        }
    }
    
    // Business Pay Bill
    suspend fun businessPayBill(
        initiator: String,
        securityCredential: String,
        commandID: String,
        amount: Int,
        partyA: String,
        partyB: String,
        remarks: String,
        queueTimeOutURL: String,
        resultURL: String,
        occasion: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<BusinessPayBillResponse> {
        return try {
            println("🔐 Getting access token for Business Pay Bill...")
            val token = authService.getValidToken(clientId, clientSecret)
                ?: return DarajaResult.Error("Failed to get access token")

            println("✅ Access token received for Business Pay Bill: ${token.take(20)}...")

            val request = BusinessPayBillRequest(
                initiator = initiator,
                securityCredential = securityCredential,
                commandID = commandID,
                amount = amount,
                partyA = partyA,
                partyB = partyB,
                remarks = remarks,
                queueTimeOutURL = queueTimeOutURL,
                resultURL = resultURL,
                occasion = occasion
            )

            println("💼 Processing business pay bill...")
            println("   Amount: $amount KES")
            println("   Party A: $partyA")
            println("   Party B: $partyB")
            println("   Command ID: $commandID")
            println("   Initiator: $initiator")

            val response = client.post("https://sandbox.safaricom.co.ke/mpesa/b2c/v1/paymentrequest") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }
            
            val responseText = response.body<String>()
            println("📡 Business Pay Bill raw response: $responseText")
            
            val json = Json { 
                ignoreUnknownKeys = true
                isLenient = true
            }
            
            val payBillResponse = json.decodeFromString<BusinessPayBillResponse>(responseText)
            
            println("✅ Business Pay Bill response received")
            DarajaResult.Success(payBillResponse)
        } catch (e: Exception) {
            println("❌ Business Pay Bill error: ${e.message}")
            e.printStackTrace()
            DarajaResult.Error("Business Pay Bill failed: ${e.message}")
        }
    }
    
    // Business Buy Goods
    suspend fun businessBuyGoods(
        initiator: String,
        securityCredential: String,
        commandID: String,
        amount: Int,
        partyA: String,
        partyB: String,
        remarks: String,
        queueTimeOutURL: String,
        resultURL: String,
        occasion: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<BusinessBuyGoodsResponse> {
        return try {
            println("🔐 Getting access token for Business Buy Goods...")
            val token = authService.getValidToken(clientId, clientSecret)
                ?: return DarajaResult.Error("Failed to get access token")

            println("✅ Access token received for Business Buy Goods: ${token.take(20)}...")

            val request = BusinessBuyGoodsRequest(
                initiator = initiator,
                securityCredential = securityCredential,
                commandID = commandID,
                amount = amount,
                partyA = partyA,
                partyB = partyB,
                remarks = remarks,
                queueTimeOutURL = queueTimeOutURL,
                resultURL = resultURL,
                occasion = occasion
            )

            println("🛒 Processing business buy goods...")
            println("   Amount: $amount KES")
            println("   Party A: $partyA")
            println("   Party B: $partyB")
            println("   Command ID: $commandID")
            println("   Initiator: $initiator")

            val response = client.post("https://sandbox.safaricom.co.ke/mpesa/b2c/v1/paymentrequest") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }
            
            val responseText = response.body<String>()
            println("📡 Business Buy Goods raw response: $responseText")
            
            val json = Json { 
                ignoreUnknownKeys = true
                isLenient = true
            }
            
            val buyGoodsResponse = json.decodeFromString<BusinessBuyGoodsResponse>(responseText)
            
            println("✅ Business Buy Goods response received")
            DarajaResult.Success(buyGoodsResponse)
        } catch (e: Exception) {
            println("❌ Business Buy Goods error: ${e.message}")
            e.printStackTrace()
            DarajaResult.Error("Business Buy Goods failed: ${e.message}")
        }
    }
    
    // Bill Manager
    suspend fun billManager(
        initiator: String,
        securityCredential: String,
        commandID: String,
        amount: Int,
        partyA: String,
        partyB: String,
        remarks: String,
        queueTimeOutURL: String,
        resultURL: String,
        occasion: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<BillManagerResponse> {
        return try {
            println("🔐 Getting access token for Bill Manager...")
            val token = authService.getValidToken(clientId, clientSecret)
                ?: return DarajaResult.Error("Failed to get access token")

            println("✅ Access token received for Bill Manager: ${token.take(20)}...")

            val request = BillManagerRequest(
                initiator = initiator,
                securityCredential = securityCredential,
                commandID = commandID,
                amount = amount,
                partyA = partyA,
                partyB = partyB,
                remarks = remarks,
                queueTimeOutURL = queueTimeOutURL,
                resultURL = resultURL,
                occasion = occasion
            )

            println("📋 Processing bill manager...")
            println("   Amount: $amount KES")
            println("   Party A: $partyA")
            println("   Party B: $partyB")
            println("   Command ID: $commandID")
            println("   Initiator: $initiator")

            val response = client.post("https://sandbox.safaricom.co.ke/mpesa/b2c/v1/paymentrequest") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }
            
            val responseText = response.body<String>()
            println("📡 Bill Manager raw response: $responseText")
            
            val json = Json { 
                ignoreUnknownKeys = true
                isLenient = true
            }
            
            val billManagerResponse = json.decodeFromString<BillManagerResponse>(responseText)
            
            println("✅ Bill Manager response received")
            DarajaResult.Success(billManagerResponse)
        } catch (e: Exception) {
            println("❌ Bill Manager error: ${e.message}")
            e.printStackTrace()
            DarajaResult.Error("Bill Manager failed: ${e.message}")
        }
    }
    
    // B2B Express CheckOut
    suspend fun b2bExpressCheckOut(
        initiator: String,
        securityCredential: String,
        commandID: String,
        amount: Int,
        partyA: String,
        partyB: String,
        remarks: String,
        queueTimeOutURL: String,
        resultURL: String,
        occasion: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<B2BExpressCheckOutResponse> {
        return try {
            println("🔐 Getting access token for B2B Express CheckOut...")
            val token = authService.getValidToken(clientId, clientSecret)
                ?: return DarajaResult.Error("Failed to get access token")

            println("✅ Access token received for B2B Express CheckOut: ${token.take(20)}...")

            val request = B2BExpressCheckOutRequest(
                initiator = initiator,
                securityCredential = securityCredential,
                commandID = commandID,
                amount = amount,
                partyA = partyA,
                partyB = partyB,
                remarks = remarks,
                queueTimeOutURL = queueTimeOutURL,
                resultURL = resultURL,
                occasion = occasion
            )

            println("🏢 Processing B2B Express CheckOut...")
            println("   Amount: $amount KES")
            println("   Party A: $partyA")
            println("   Party B: $partyB")
            println("   Command ID: $commandID")
            println("   Initiator: $initiator")

            val response = client.post("https://sandbox.safaricom.co.ke/mpesa/b2c/v1/paymentrequest") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }
            
            val responseText = response.body<String>()
            println("📡 B2B Express CheckOut raw response: $responseText")
            
            val json = Json { 
                ignoreUnknownKeys = true
                isLenient = true
            }
            
            val b2bExpressCheckOutResponse = json.decodeFromString<B2BExpressCheckOutResponse>(responseText)
            
            println("✅ B2B Express CheckOut response received")
            DarajaResult.Success(b2bExpressCheckOutResponse)
        } catch (e: Exception) {
            println("❌ B2B Express CheckOut error: ${e.message}")
            e.printStackTrace()
            DarajaResult.Error("B2B Express CheckOut failed: ${e.message}")
        }
    }
    
    // B2C Account Top Up
    suspend fun b2cAccountTopUp(
        initiator: String,
        securityCredential: String,
        commandID: String,
        amount: Int,
        partyA: String,
        partyB: String,
        remarks: String,
        queueTimeOutURL: String,
        resultURL: String,
        occasion: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<B2CAccountTopUpResponse> {
        return try {
            println("🔐 Getting access token for B2C Account Top Up...")
            val token = authService.getValidToken(clientId, clientSecret)
                ?: return DarajaResult.Error("Failed to get access token")

            println("✅ Access token received for B2C Account Top Up: ${token.take(20)}...")

            val request = B2CAccountTopUpRequest(
                initiator = initiator,
                securityCredential = securityCredential,
                commandID = commandID,
                amount = amount,
                partyA = partyA,
                partyB = partyB,
                remarks = remarks,
                queueTimeOutURL = queueTimeOutURL,
                resultURL = resultURL,
                occasion = occasion
            )

            println("💰 Processing B2C Account Top Up...")
            println("   Amount: $amount KES")
            println("   Party A: $partyA")
            println("   Party B: $partyB")
            println("   Command ID: $commandID")
            println("   Initiator: $initiator")

            val response = client.post("https://sandbox.safaricom.co.ke/mpesa/b2c/v1/paymentrequest") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }
            
            val responseText = response.body<String>()
            println("📡 B2C Account Top Up raw response: $responseText")
            
            val json = Json { 
                ignoreUnknownKeys = true
                isLenient = true
            }
            
            val b2cAccountTopUpResponse = json.decodeFromString<B2CAccountTopUpResponse>(responseText)
            
            println("✅ B2C Account Top Up response received")
            DarajaResult.Success(b2cAccountTopUpResponse)
        } catch (e: Exception) {
            println("❌ B2C Account Top Up error: ${e.message}")
            e.printStackTrace()
            DarajaResult.Error("B2C Account Top Up failed: ${e.message}")
        }
    }
    
    // M-Pesa Ratiba
    suspend fun mpesaRatiba(
        initiator: String,
        securityCredential: String,
        commandID: String,
        amount: Int,
        partyA: String,
        partyB: String,
        remarks: String,
        queueTimeOutURL: String,
        resultURL: String,
        occasion: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<MpesaRatibaResponse> {
        return try {
            println("🔐 Getting access token for M-Pesa Ratiba...")
            val token = authService.getValidToken(clientId, clientSecret)
                ?: return DarajaResult.Error("Failed to get access token")

            println("✅ Access token received for M-Pesa Ratiba: ${token.take(20)}...")

            val request = MpesaRatibaRequest(
                initiator = initiator,
                securityCredential = securityCredential,
                commandID = commandID,
                amount = amount,
                partyA = partyA,
                partyB = partyB,
                remarks = remarks,
                queueTimeOutURL = queueTimeOutURL,
                resultURL = resultURL,
                occasion = occasion
            )

            println("📅 Processing M-Pesa Ratiba...")
            println("   Amount: $amount KES")
            println("   Party A: $partyA")
            println("   Party B: $partyB")
            println("   Command ID: $commandID")
            println("   Initiator: $initiator")

            val response = client.post("https://sandbox.safaricom.co.ke/mpesa/b2c/v1/paymentrequest") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }
            
            val responseText = response.body<String>()
            println("📡 M-Pesa Ratiba raw response: $responseText")
            
            val json = Json { 
                ignoreUnknownKeys = true
                isLenient = true
            }
            
            val mpesaRatibaResponse = json.decodeFromString<MpesaRatibaResponse>(responseText)
            
            println("✅ M-Pesa Ratiba response received")
            DarajaResult.Success(mpesaRatibaResponse)
        } catch (e: Exception) {
            println("❌ M-Pesa Ratiba error: ${e.message}")
            e.printStackTrace()
            DarajaResult.Error("M-Pesa Ratiba failed: ${e.message}")
        }
    }
    
    // B2C - Business to Customer Transfer
    suspend fun b2cTransfer(
        initiatorName: String,
        securityCredential: String,
        commandID: String,
        amount: Int,
        partyA: String,
        partyB: String,
        remarks: String,
        queueTimeOutURL: String,
        resultURL: String,
        occasion: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<B2CResponse> {
        return try {
            println("🔐 Getting access token for B2C...")
            val token = authService.getValidToken(clientId, clientSecret)
                ?: return DarajaResult.Error("Failed to get access token")

            println("✅ Access token received for B2C: ${token.take(20)}...")

            val request = B2CRequest(
                initiatorName = initiatorName,
                securityCredential = securityCredential,
                commandID = commandID,
                amount = amount,
                partyA = partyA,
                partyB = partyB,
                remarks = remarks,
                queueTimeOutURL = queueTimeOutURL,
                resultURL = resultURL,
                occasion = occasion
            )

            println("💰 Sending B2C transfer request...")
            println("   Amount: $amount KES")
            println("   From: $partyA")
            println("   To: $partyB")
            println("   Command: $commandID")

            val response = client.post("https://sandbox.safaricom.co.ke/mpesa/b2c/v1/paymentrequest") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }
            
            val responseText = response.body<String>()
            println("📡 B2C raw response: $responseText")
            
            val json = Json { 
                ignoreUnknownKeys = true
                isLenient = true
            }
            
            val b2cResponse = json.decodeFromString<B2CResponse>(responseText)
            
            println("✅ B2C response received")
            DarajaResult.Success(b2cResponse)
        } catch (e: Exception) {
            println("❌ B2C error: ${e.message}")
            e.printStackTrace()
            DarajaResult.Error("B2C transfer failed: ${e.message}")
        }
    }
    
    // Dynamic QR - Generate QR Code for payments
    suspend fun generateDynamicQR(
        merchantName: String,
        refNo: String,
        amount: Int,
        trxCode: String,
        cpi: String,
        size: String = "300",
        clientId: String,
        clientSecret: String
    ): DarajaResult<DynamicQRResponse> {
        return try {
            println("🔐 Getting access token for Dynamic QR...")
            val token = authService.getValidToken(clientId, clientSecret)
                ?: return DarajaResult.Error("Failed to get access token")

            println("✅ Access token received for Dynamic QR: ${token.take(20)}...")

            val request = DynamicQRRequest(
                merchantName = merchantName,
                refNo = refNo,
                amount = amount,
                trxCode = trxCode,
                cpi = cpi,
                size = size
            )

            println("📱 Generating Dynamic QR...")
            println("   Merchant: $merchantName")
            println("   Amount: $amount KES")
            println("   Reference: $refNo")
            println("   Transaction Code: $trxCode")

            val response = client.post("https://sandbox.safaricom.co.ke/mpesa/qrcode/v1/generate") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }
            
            val responseText = response.body<String>()
            println("📡 Dynamic QR raw response: $responseText")
            
            val json = Json { 
                ignoreUnknownKeys = true
                isLenient = true
            }
            
            val qrResponse = json.decodeFromString<DynamicQRResponse>(responseText)
            
            println("✅ Dynamic QR response received")
            DarajaResult.Success(qrResponse)
        } catch (e: Exception) {
            println("❌ Dynamic QR error: ${e.message}")
            e.printStackTrace()
            DarajaResult.Error("Dynamic QR generation failed: ${e.message}")
        }
    }
    
    // Utility functions
    fun getTimestamp(): String {
        return Clock.System.now().getDarajaTimestamp()
    }
    
    fun generatePassword(businessShortCode: String, passKey: String): String {
        val timestamp = Clock.System.now().getDarajaTimestamp()
        val password = "$businessShortCode$passKey$timestamp"
        return password.encodeBase64()
    }


    fun close() {
        client.close()
    }
}
