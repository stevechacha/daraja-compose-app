package com.chacha.darajacmp.network

import com.chacha.darajacmp.models.*
import com.chacha.darajacmp.network.response.*
import com.chacha.darajacmp.utils.DarajaEndPoints
import com.chacha.darajacmp.utils.DarajaResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.*
import kotlinx.serialization.json.Json

class DarajaApiCallService(
    private val consumerKey: String,
    private val consumerSecret: String,
) {
    private val httpClient = HttpClient {
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

    internal suspend fun fetchAccessToken(): DarajaResult<AuthResponse> = dataResultSafeApiCall {
        val key = "$consumerKey:$consumerSecret"
        val base64Key = key.encodeBase64()

        val response = httpClient.get(DarajaEndPoints.SANDBOX_AUTH_URL) {
            headers {
                append(HttpHeaders.Authorization, "Basic $base64Key")
                append(HttpHeaders.Accept, "application/json")
            }
        }

        response.body<AuthResponse>()
    }

    internal suspend fun stkPush(stkPushRequest: STKPushRequest): DarajaResult<STKPushResponse> =
        dataResultSafeApiCall {
            when (val tokenResult = fetchAccessToken()) {
                is DarajaResult.Success -> {
                    val accessToken = tokenResult.data.accessToken

                    val response = httpClient.post(DarajaEndPoints.SANDBOX_STK_PUSH_URL) {
            headers {
                append(HttpHeaders.Authorization, "Bearer $accessToken")
                            append(HttpHeaders.ContentType, "application/json")
                        }
                        setBody(stkPushRequest)
                    }
                    response.body<STKPushResponse>()
                }


                is DarajaResult.Error -> {
                    throw Exception("Failed to get access token: ${tokenResult.error}")
                }
            }
        }

    internal suspend fun initiateMpesaExpress(queryMpesaExpressRequest: STKPushRequest): DarajaResult<STKPushResponse> =
        dataResultSafeApiCall {

            when(val tokenResult = fetchAccessToken()) {
                is DarajaResult.Error -> {
                    throw Exception("Failed to get access token: ${tokenResult.error}")
                }
                is DarajaResult.Success -> {
                    val accessToken = tokenResult.data.accessToken

                    return@dataResultSafeApiCall httpClient.post(urlString = DarajaEndPoints.SANDBOX_STK_PUSH_URL) {
                        headers { append(HttpHeaders.Authorization, "Bearer $accessToken") }
                        setBody(queryMpesaExpressRequest)
        }.body()
                }
            }
        }

    internal suspend fun stkQuery(stkQueryRequest: STKQueryRequest): DarajaResult<STKQueryResponse> =
        dataResultSafeApiCall {
            when (val tokenResult = fetchAccessToken()) {
                is DarajaResult.Success -> {
                    val accessToken = tokenResult.data.accessToken

                    val response = httpClient.post(DarajaEndPoints.SANDBOX_STK_QUERY_URL) {
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $accessToken")
                            append(HttpHeaders.ContentType, "application/json")
                        }
                        setBody(stkQueryRequest)
                    }
                    response.body<STKQueryResponse>()
                }

                is DarajaResult.Error -> {
                    throw Exception("Failed to get access token: ${tokenResult.error}")
                }
            }
        }

    // C2B Register URL
    internal suspend fun c2bRegisterURL(
        c2bRegisterRequest: C2BRegisterRequest
    ): DarajaResult<C2BRegisterResponse> = dataResultSafeApiCall {
        when (val tokenResult = fetchAccessToken()) {
            is DarajaResult.Success -> {
                val accessToken = tokenResult.data.accessToken
                val response = httpClient.post(DarajaEndPoints.SANDBOX_C2B_REGISTER_URL) {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $accessToken")
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    setBody(c2bRegisterRequest)
                }
                response.body<C2BRegisterResponse>()
            }
            is DarajaResult.Error -> {
                throw Exception("Failed to get access token: ${tokenResult.error}")
            }
        }
    }

    // C2B Simulate
    internal suspend fun c2bSimulate(
        c2bSimulateRequest: C2BSimulateRequest
    ): DarajaResult<C2BSimulateResponse> = dataResultSafeApiCall {
        when (val tokenResult = fetchAccessToken()) {
            is DarajaResult.Success -> {
                val accessToken = tokenResult.data.accessToken
                val response = httpClient.post("https://sandbox.safaricom.co.ke/mpesa/c2b/v1/simulate") {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $accessToken")
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    setBody(c2bSimulateRequest)
                }
                response.body<C2BSimulateResponse>()
            }
            is DarajaResult.Error -> {
                throw Exception("Failed to get access token: ${tokenResult.error}")
            }
        }
    }

    // B2C Transfer
    internal suspend fun b2cTransfer(
        b2cRequest: B2CRequest
    ): DarajaResult<B2CResponse> = dataResultSafeApiCall {
        when (val tokenResult = fetchAccessToken()) {
            is DarajaResult.Success -> {
                val accessToken = tokenResult.data.accessToken
                val response = httpClient.post(DarajaEndPoints.SANDBOX_B2C) {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $accessToken")
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    setBody(b2cRequest)
                }
                response.body<B2CResponse>()
            }
            is DarajaResult.Error -> {
                throw Exception("Failed to get access token: ${tokenResult.error}")
            }
        }
    }

    // Dynamic QR
    internal suspend fun generateDynamicQR(
        dynamicQRRequest: DynamicQRRequest
    ): DarajaResult<DynamicQRResponse> = dataResultSafeApiCall {
        when (val tokenResult = fetchAccessToken()) {
            is DarajaResult.Success -> {
                val accessToken = tokenResult.data.accessToken
                val response = httpClient.post(DarajaEndPoints.SANDBOX_DYNAMIC_QR_CODE) {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $accessToken")
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    setBody(dynamicQRRequest)
                }
                response.body<DynamicQRResponse>()
            }
            is DarajaResult.Error -> {
                throw Exception("Failed to get access token: ${tokenResult.error}")
            }
        }
    }

    // Account Balance
    internal suspend fun accountBalance(
        accountBalanceRequest: AccountBalanceRequest
    ): DarajaResult<AccountBalanceResponse> = dataResultSafeApiCall {
        when (val tokenResult = fetchAccessToken()) {
            is DarajaResult.Success -> {
                val accessToken = tokenResult.data.accessToken
                val response = httpClient.post(DarajaEndPoints.SANDBOX_ACCOUNT_BALANCE_URL) {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $accessToken")
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    setBody(accountBalanceRequest)
                }
                response.body<AccountBalanceResponse>()
            }
            is DarajaResult.Error -> {
                throw Exception("Failed to get access token: ${tokenResult.error}")
            }
        }
    }

    // Transaction Status
    internal suspend fun transactionStatus(
        transactionStatusRequest: TransactionStatusRequest
    ): DarajaResult<TransactionStatusResponse> = dataResultSafeApiCall {
        when (val tokenResult = fetchAccessToken()) {
            is DarajaResult.Success -> {
                val accessToken = tokenResult.data.accessToken
                val response = httpClient.post(DarajaEndPoints.SANDBOX_TRANSACTION_STATUS_URL) {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $accessToken")
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    setBody(transactionStatusRequest)
                }
                response.body<TransactionStatusResponse>()
            }
            is DarajaResult.Error -> {
                throw Exception("Failed to get access token: ${tokenResult.error}")
            }
        }
    }

    // Reversal
    internal suspend fun reversal(
        reversalRequest: ReversalRequest
    ): DarajaResult<ReversalResponse> = dataResultSafeApiCall {
        when (val tokenResult = fetchAccessToken()) {
            is DarajaResult.Success -> {
                val accessToken = tokenResult.data.accessToken
                val response = httpClient.post(DarajaEndPoints.SANDBOX_REVERSAL_URL) {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $accessToken")
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    setBody(reversalRequest)
                }
                response.body<ReversalResponse>()
            }
            is DarajaResult.Error -> {
                throw Exception("Failed to get access token: ${tokenResult.error}")
            }
        }
    }

    // Tax Remittance
    internal suspend fun taxRemittance(
        taxRemittanceRequest: TaxRemittanceRequest
    ): DarajaResult<TaxRemittanceResponse> = dataResultSafeApiCall {
        when (val tokenResult = fetchAccessToken()) {
            is DarajaResult.Success -> {
                val accessToken = tokenResult.data.accessToken
                val response = httpClient.post(DarajaEndPoints.SANDBOX_TAX_REMITTANCE) {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $accessToken")
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    setBody(taxRemittanceRequest)
                }
                response.body<TaxRemittanceResponse>()
            }
            is DarajaResult.Error -> {
                throw Exception("Failed to get access token: ${tokenResult.error}")
            }
        }
    }

    // Business Pay Bill
    internal suspend fun businessPayBill(
        businessPayBillRequest: BusinessPayBillRequest
    ): DarajaResult<BusinessPayBillResponse> = dataResultSafeApiCall {
        when (val tokenResult = fetchAccessToken()) {
            is DarajaResult.Success -> {
                val accessToken = tokenResult.data.accessToken
                val response = httpClient.post(DarajaEndPoints.SANDBOX_BUSINESS_PAY_BILL) {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $accessToken")
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    setBody(businessPayBillRequest)
                }
                response.body<BusinessPayBillResponse>()
            }
            is DarajaResult.Error -> {
                throw Exception("Failed to get access token: ${tokenResult.error}")
            }
        }
    }

    // Business Buy Goods
    internal suspend fun businessBuyGoods(
        businessBuyGoodsRequest: BusinessBuyGoodsRequest
    ): DarajaResult<BusinessBuyGoodsResponse> = dataResultSafeApiCall {
        when (val tokenResult = fetchAccessToken()) {
            is DarajaResult.Success -> {
                val accessToken = tokenResult.data.accessToken
                val response = httpClient.post(DarajaEndPoints.SANDBOX_BUSINESS_BUY_GOODS) {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $accessToken")
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    setBody(businessBuyGoodsRequest)
                }
                response.body<BusinessBuyGoodsResponse>()
            }
            is DarajaResult.Error -> {
                throw Exception("Failed to get access token: ${tokenResult.error}")
            }
        }
    }

    // Bill Manager
    internal suspend fun billManager(
        billManagerRequest: BillManagerRequest
    ): DarajaResult<BillManagerResponse> = dataResultSafeApiCall {
        when (val tokenResult = fetchAccessToken()) {
            is DarajaResult.Success -> {
                val accessToken = tokenResult.data.accessToken
                val response = httpClient.post(DarajaEndPoints.SANDBOX_BILL_MANAGER) {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $accessToken")
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    setBody(billManagerRequest)
                }
                response.body<BillManagerResponse>()
            }
            is DarajaResult.Error -> {
                throw Exception("Failed to get access token: ${tokenResult.error}")
            }
        }
    }

    // B2B Express CheckOut
    internal suspend fun b2bExpressCheckOut(
        b2bExpressCheckOutRequest: B2BExpressCheckOutRequest
    ): DarajaResult<B2BExpressCheckOutResponse> = dataResultSafeApiCall {
        when (val tokenResult = fetchAccessToken()) {
            is DarajaResult.Success -> {
                val accessToken = tokenResult.data.accessToken
                val response = httpClient.post(DarajaEndPoints.SANDBOX_B2B_EXPRESS_CHECKOUT) {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $accessToken")
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    setBody(b2bExpressCheckOutRequest)
                }
                response.body<B2BExpressCheckOutResponse>()
            }
            is DarajaResult.Error -> {
                throw Exception("Failed to get access token: ${tokenResult.error}")
            }
        }
    }

    // B2C Account Top Up
    internal suspend fun b2cAccountTopUp(
        b2cAccountTopUpRequest: B2CAccountTopUpRequest
    ): DarajaResult<B2CAccountTopUpResponse> = dataResultSafeApiCall {
        when (val tokenResult = fetchAccessToken()) {
            is DarajaResult.Success -> {
                val accessToken = tokenResult.data.accessToken
                val response = httpClient.post(DarajaEndPoints.SANDBOX_B2C_ACCOUNT_TOP_UP) {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $accessToken")
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    setBody(b2cAccountTopUpRequest)
                }
                response.body<B2CAccountTopUpResponse>()
            }
            is DarajaResult.Error -> {
                throw Exception("Failed to get access token: ${tokenResult.error}")
            }
        }
    }

    // M-Pesa Ratiba
    internal suspend fun mpesaRatiba(
        mpesaRatibaRequest: MpesaRatibaRequest
    ): DarajaResult<MpesaRatibaResponse> = dataResultSafeApiCall {
        when (val tokenResult = fetchAccessToken()) {
            is DarajaResult.Success -> {
                val accessToken = tokenResult.data.accessToken
                val response = httpClient.post(DarajaEndPoints.SANDBOX_M_RATIBA) {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $accessToken")
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    setBody(mpesaRatibaRequest)
                }
                response.body<MpesaRatibaResponse>()
            }
            is DarajaResult.Error -> {
                throw Exception("Failed to get access token: ${tokenResult.error}")
            }
        }
    }
}