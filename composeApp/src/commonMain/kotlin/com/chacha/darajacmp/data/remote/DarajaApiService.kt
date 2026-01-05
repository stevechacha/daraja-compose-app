package com.chacha.darajacmp.data.remote

import com.chacha.darajacmp.data.dto.request.*
import com.chacha.darajacmp.data.dto.response.*
import com.chacha.darajacmp.utils.AppLogger
import com.chacha.darajacmp.utils.DarajaConfig
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
import io.ktor.util.encodeBase64
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json

class DarajaApiService(
    private val authService: AuthService,
    private val darajaApiCallService: DarajaApiCallService
) {
    
    private val jsonConfig = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
        encodeDefaults = true
        coerceInputValues = true
    }
    
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(jsonConfig)
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
        install(DefaultRequest) {
            contentType(ContentType.Application.Json)
        }
    }

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
        return executeApiCall(
            operation = "STK Push",
            url = DarajaConfig.SANDBOX_STK_PUSH_URL,
            clientId = clientId,
            clientSecret = clientSecret
        ) { token ->
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
            
            AppLogger.debug("STK Push: Amount=$amount, Phone=$phoneNumber, BusinessCode=$businessShortCode")
            
            client.post(DarajaConfig.SANDBOX_STK_PUSH_URL) {
                headers { append(HttpHeaders.Authorization, "Bearer $token") }
                setBody(request)
            }.body<String>().let { jsonConfig.decodeFromString<STKPushResponse>(it) }
        }
    }
    
    suspend fun stkQuery(
        businessShortCode: String,
        password: String,
        timestamp: String,
        checkoutRequestID: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<STKQueryResponse> {
        return executeApiCall(
            operation = "STK Query",
            url = DarajaConfig.SANDBOX_STK_QUERY_URL,
            clientId = clientId,
            clientSecret = clientSecret
        ) { token ->
            val request = STKQueryRequest(
                businessShortCode = businessShortCode,
                password = password,
                timestamp = timestamp,
                checkoutRequestID = checkoutRequestID
            )
            
            AppLogger.debug("STK Query: BusinessCode=$businessShortCode, CheckoutRequestID=$checkoutRequestID")
            
            client.post(DarajaConfig.SANDBOX_STK_QUERY_URL) {
                headers { append(HttpHeaders.Authorization, "Bearer $token") }
                setBody(request)
            }.body<String>().let { jsonConfig.decodeFromString<STKQueryResponse>(it) }
        }
    }
    
    suspend fun c2bRegisterURL(
        shortCode: String,
        responseType: String,
        confirmationURL: String,
        validationURL: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<C2BRegisterResponse> {
        return executeApiCall(
            operation = "C2B Register",
            url = DarajaConfig.SANDBOX_C2B_REGISTER_URL,
            clientId = clientId,
            clientSecret = clientSecret
        ) { token ->
            val request = C2BRegisterRequest(
                shortCode = shortCode,
                responseType = responseType,
                confirmationURL = confirmationURL,
                validationURL = validationURL
            )
            
            client.post(DarajaConfig.SANDBOX_C2B_REGISTER_URL) {
                headers { append(HttpHeaders.Authorization, "Bearer $token") }
                setBody(request)
            }.body<String>().let { jsonConfig.decodeFromString<C2BRegisterResponse>(it) }
        }
    }
    
    suspend fun c2bSimulate(
        shortCode: String,
        commandID: String,
        amount: Int,
        msisdn: String,
        billRefNumber: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<C2BSimulateResponse> {
        return executeApiCall(
            operation = "C2B Simulate",
            url = DarajaConfig.SANDBOX_C2B_SIMULATE_URL,
            clientId = clientId,
            clientSecret = clientSecret
        ) { token ->
            val request = C2BSimulateRequest(
                shortCode = shortCode,
                commandID = commandID,
                amount = amount,
                msisdn = msisdn,
                billRefNumber = billRefNumber
            )
            
            client.post(DarajaConfig.SANDBOX_C2B_SIMULATE_URL) {
                headers { append(HttpHeaders.Authorization, "Bearer $token") }
                setBody(request)
            }.body<C2BSimulateResponse>()
        }
    }
    
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
        return executeApiCall(
            operation = "B2C",
            url = DarajaConfig.SANDBOX_B2C,
            clientId = clientId,
            clientSecret = clientSecret
        ) { token ->
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
            
            client.post(DarajaConfig.SANDBOX_B2C) {
                headers { append(HttpHeaders.Authorization, "Bearer $token") }
                setBody(request)
            }.body<B2CResponse>()
        }
    }
    
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
        return executeApiCall(
            operation = "Account Balance",
            url = DarajaConfig.SANDBOX_ACCOUNT_BALANCE_URL,
            clientId = clientId,
            clientSecret = clientSecret
        ) { token ->
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
            
            client.post(DarajaConfig.SANDBOX_ACCOUNT_BALANCE_URL) {
                headers { append(HttpHeaders.Authorization, "Bearer $token") }
                setBody(request)
            }.body<String>().let { jsonConfig.decodeFromString<AccountBalanceResponse>(it) }
        }
    }
    
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
        return executeApiCall(
            operation = "Transaction Status",
            url = DarajaConfig.SANDBOX_TRANSACTION_STATUS_URL,
            clientId = clientId,
            clientSecret = clientSecret
        ) { token ->
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
            
            client.post(DarajaConfig.SANDBOX_TRANSACTION_STATUS_URL) {
                headers { append(HttpHeaders.Authorization, "Bearer $token") }
                setBody(request)
            }.body<String>().let { jsonConfig.decodeFromString<TransactionStatusResponse>(it) }
        }
    }
    
    suspend fun reversal(
        initiator: String,
        securityCredential: String,
        commandID: String,
        transactionID: String,
        amount: String,
        receiverParty: String,
        recieverIdentifierType: String,
        resultURL: String,
        queueTimeOutURL: String,
        remarks: String,
        occasion: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<ReversalResponse> {
        return executeApiCall(
            operation = "Reversal",
            url = DarajaConfig.SANDBOX_REVERSAL_URL,
            clientId = clientId,
            clientSecret = clientSecret
        ) { token ->
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
            
            client.post(DarajaConfig.SANDBOX_REVERSAL_URL) {
                headers { append(HttpHeaders.Authorization, "Bearer $token") }
                setBody(request)
            }.body<String>().let { jsonConfig.decodeFromString<ReversalResponse>(it) }
        }
    }
    
    suspend fun taxRemittance(
        initiator: String,
        securityCredential: String,
        commandID: String,
        senderIdentifierType: String,
        receiverIdentifierType: String,
        amount: String,
        partyA: String,
        partyB: String,
        accountReference: String,
        remarks: String,
        queueTimeOutURL: String,
        resultURL: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<TaxRemittanceResponse> {
        return executeApiCall(
            operation = "Tax Remittance",
            url = DarajaConfig.SANDBOX_TAX_REMITTANCE,
            clientId = clientId,
            clientSecret = clientSecret
        ) { token ->
            val request = TaxRemittanceRequest(
                initiator = initiator,
                securityCredential = securityCredential,
                commandID = commandID,
                senderIdentifierType = senderIdentifierType,
                receiverIdentifierType = receiverIdentifierType,
                amount = amount,
                partyA = partyA,
                partyB = partyB,
                accountReference = accountReference,
                remarks = remarks,
                queueTimeOutURL = queueTimeOutURL,
                resultURL = resultURL
            )
            
            client.post(DarajaConfig.SANDBOX_TAX_REMITTANCE) {
                headers { append(HttpHeaders.Authorization, "Bearer $token") }
                setBody(request)
            }.body<String>().let { jsonConfig.decodeFromString<TaxRemittanceResponse>(it) }
        }
    }
    
    suspend fun businessPayBill(
        initiator: String,
        securityCredential: String,
        commandID: String,
        senderIdentifierType: String,
        receiverIdentifierType: String,
        amount: String,
        partyA: String,
        partyB: String,
        accountReference: String,
        requester: String,
        remarks: String,
        queueTimeOutURL: String,
        resultURL: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<BusinessPayBillResponse> {
        return executeApiCall(
            operation = "Business Pay Bill",
            url = DarajaConfig.SANDBOX_BUSINESS_PAY_BILL,
            clientId = clientId,
            clientSecret = clientSecret
        ) { token ->
            val request = BusinessPayBillRequest(
                initiator = initiator,
                securityCredential = securityCredential,
                commandID = commandID,
                senderIdentifierType = senderIdentifierType,
                receiverIdentifierType = receiverIdentifierType,
                amount = amount,
                partyA = partyA,
                partyB = partyB,
                accountReference = accountReference,
                requester = requester,
                remarks = remarks,
                queueTimeOutURL = queueTimeOutURL,
                resultURL = resultURL
            )
            
            client.post(DarajaConfig.SANDBOX_BUSINESS_PAY_BILL) {
                headers { append(HttpHeaders.Authorization, "Bearer $token") }
                setBody(request)
            }.body<String>().let { jsonConfig.decodeFromString<BusinessPayBillResponse>(it) }
        }
    }
    
    suspend fun businessBuyGoods(
        initiator: String,
        securityCredential: String,
        commandID: String,
        senderIdentifierType: String,
        receiverIdentifierType: String,
        amount: String,
        partyA: String,
        partyB: String,
        accountReference: String,
        requester: String,
        remarks: String,
        queueTimeOutURL: String,
        resultURL: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<BusinessBuyGoodsResponse> {
        return executeApiCall(
            operation = "Business Buy Goods",
            url = DarajaConfig.SANDBOX_BUSINESS_BUY_GOODS,
            clientId = clientId,
            clientSecret = clientSecret
        ) { token ->
            val request = BusinessBuyGoodsRequest(
                initiator = initiator,
                securityCredential = securityCredential,
                commandID = commandID,
                senderIdentifierType = senderIdentifierType,
                receiverIdentifierType = receiverIdentifierType,
                amount = amount,
                partyA = partyA,
                partyB = partyB,
                accountReference = accountReference,
                requester = requester,
                remarks = remarks,
                queueTimeOutURL = queueTimeOutURL,
                resultURL = resultURL
            )
            
            client.post(DarajaConfig.SANDBOX_BUSINESS_BUY_GOODS) {
                headers { append(HttpHeaders.Authorization, "Bearer $token") }
                setBody(request)
            }.body<String>().let { jsonConfig.decodeFromString<BusinessBuyGoodsResponse>(it) }
        }
    }
    
    suspend fun billManager(
        shortcode: String,
        email: String,
        officialContact: String,
        sendReminders: String,
        logo: String,
        callbackurl: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<BillManagerResponse> {
        return executeApiCall(
            operation = "Bill Manager",
            url = DarajaConfig.SANDBOX_BILL_MANAGER,
            clientId = clientId,
            clientSecret = clientSecret
        ) { token ->
            val request = BillManagerRequest(
                shortcode = shortcode,
                email = email,
                officialContact = officialContact,
                sendReminders = sendReminders,
                logo = logo,
                callbackurl = callbackurl
            )
            
            client.post(DarajaConfig.SANDBOX_BILL_MANAGER) {
                headers { append(HttpHeaders.Authorization, "Bearer $token") }
                setBody(request)
            }.body<String>().let { jsonConfig.decodeFromString<BillManagerResponse>(it) }
        }
    }
    
    suspend fun b2bExpressCheckOut(
        primaryShortCode: String,
        receiverShortCode: String,
        amount: String,
        paymentRef: String,
        callbackUrl: String,
        partnerName: String,
        requestRefID: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<B2BExpressCheckOutResponse> {
        return executeApiCall(
            operation = "B2B Express CheckOut",
            url = DarajaConfig.SANDBOX_B2B_EXPRESS_CHECKOUT,
            clientId = clientId,
            clientSecret = clientSecret
        ) { token ->
            val request = B2BExpressCheckOutRequest(
                primaryShortCode = primaryShortCode,
                receiverShortCode = receiverShortCode,
                amount = amount,
                paymentRef = paymentRef,
                callbackUrl = callbackUrl,
                partnerName = partnerName,
                requestRefID = requestRefID
            )
            
            client.post(DarajaConfig.SANDBOX_B2B_EXPRESS_CHECKOUT) {
                headers { append(HttpHeaders.Authorization, "Bearer $token") }
                setBody(request)
            }.body<String>().let { jsonConfig.decodeFromString<B2BExpressCheckOutResponse>(it) }
        }
    }
    
    suspend fun b2cAccountTopUp(
        initiator: String,
        securityCredential: String,
        commandID: String,
        senderIdentifierType: String,
        receiverIdentifierType: String,
        amount: String,
        partyA: String,
        partyB: String,
        accountReference: String,
        requester: String,
        remarks: String,
        queueTimeOutURL: String,
        resultURL: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<B2CAccountTopUpResponse> {
        return executeApiCall(
            operation = "B2C Account Top Up",
            url = DarajaConfig.SANDBOX_B2C_ACCOUNT_TOP_UP,
            clientId = clientId,
            clientSecret = clientSecret
        ) { token ->
            val request = B2CAccountTopUpRequest(
                initiator = initiator,
                securityCredential = securityCredential,
                commandID = commandID,
                senderIdentifierType = senderIdentifierType,
                receiverIdentifierType = receiverIdentifierType,
                amount = amount,
                partyA = partyA,
                partyB = partyB,
                accountReference = accountReference,
                requester = requester,
                remarks = remarks,
                queueTimeOutURL = queueTimeOutURL,
                resultURL = resultURL
            )
            
            client.post(DarajaConfig.SANDBOX_B2C_ACCOUNT_TOP_UP) {
                headers { append(HttpHeaders.Authorization, "Bearer $token") }
                setBody(request)
            }.body<String>().let { jsonConfig.decodeFromString<B2CAccountTopUpResponse>(it) }
        }
    }
    
    suspend fun mpesaRatiba(
        standingOrderName: String,
        startDate: String,
        endDate: String,
        businessShortCode: String,
        transactionType: String,
        receiverPartyIdentifierType: String,
        amount: String,
        partyA: String,
        callBackURL: String,
        accountReference: String,
        transactionDesc: String,
        frequency: String,
        clientId: String,
        clientSecret: String
    ): DarajaResult<MpesaRatibaResponse> {
        return executeApiCall(
            operation = "M-Pesa Ratiba",
            url = DarajaConfig.SANDBOX_M_RATIBA,
            clientId = clientId,
            clientSecret = clientSecret
        ) { token ->
            val request = MpesaRatibaRequest(
                standingOrderName = standingOrderName,
                startDate = startDate,
                endDate = endDate,
                businessShortCode = businessShortCode,
                transactionType = transactionType,
                receiverPartyIdentifierType = receiverPartyIdentifierType,
                amount = amount,
                partyA = partyA,
                callBackURL = callBackURL,
                accountReference = accountReference,
                transactionDesc = transactionDesc,
                frequency = frequency
            )
            
            client.post(DarajaConfig.SANDBOX_M_RATIBA) {
                headers { append(HttpHeaders.Authorization, "Bearer $token") }
                setBody(request)
            }.body<String>().let { jsonConfig.decodeFromString<MpesaRatibaResponse>(it) }
        }
    }
    
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
        return executeApiCall(
            operation = "B2C Transfer",
            url = DarajaConfig.SANDBOX_B2C,
            clientId = clientId,
            clientSecret = clientSecret
        ) { token ->
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
            
            client.post(DarajaConfig.SANDBOX_B2C) {
                headers { append(HttpHeaders.Authorization, "Bearer $token") }
                setBody(request)
            }.body<String>().let { jsonConfig.decodeFromString<B2CResponse>(it) }
        }
    }
    
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
        return executeApiCall(
            operation = "Dynamic QR",
            url = DarajaConfig.SANDBOX_DYNAMIC_QR_CODE,
            clientId = clientId,
            clientSecret = clientSecret
        ) { token ->
            val request = DynamicQRRequest(
                merchantName = merchantName,
                refNo = refNo,
                amount = amount,
                trxCode = trxCode,
                cpi = cpi,
                size = size
            )
            
            client.post(DarajaConfig.SANDBOX_DYNAMIC_QR_CODE) {
                headers { append(HttpHeaders.Authorization, "Bearer $token") }
                setBody(request)
            }.body<String>().let { jsonConfig.decodeFromString<DynamicQRResponse>(it) }
        }
    }
    
    private suspend inline fun <T> executeApiCall(
        operation: String,
        url: String,
        clientId: String,
        clientSecret: String,
        crossinline apiCall: suspend (String) -> T
    ): DarajaResult<T> {
        return try {
            val token = authService.getValidToken(clientId, clientSecret)
                ?: return DarajaResult.Error("Failed to get access token for $operation")
            
            AppLogger.info("Executing $operation")
            val result = apiCall(token)
            AppLogger.info("$operation completed successfully")
            DarajaResult.Success(result)
        } catch (e: Exception) {
            AppLogger.error("$operation failed", e)
            DarajaResult.Error("$operation failed: ${e.message ?: "Unknown error"}")
        }
    }

    fun getTimestamp(): String = Clock.System.now().getDarajaTimestamp()
    
    fun generatePassword(businessShortCode: String, passKey: String): String {
        val timestamp = Clock.System.now().getDarajaTimestamp()
        val password = "$businessShortCode$passKey$timestamp"
        return password.encodeBase64()
    }

    fun close() {
        client.close()
    }
}
