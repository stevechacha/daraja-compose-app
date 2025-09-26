package com.chacha.darajacmp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chacha.darajacmp.models.*
import com.chacha.darajacmp.network.DarajaApiCallService
import com.chacha.darajacmp.network.response.*
import com.chacha.darajacmp.utils.DarajaResult
import com.chacha.darajacmp.utils.getDarajaTimestamp
import io.ktor.util.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

data class NewMpesaUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: String? = null,
    val stkPushResponse: STKPushResponse? = null,
    val stkQueryResponse: STKQueryResponse? = null,
    val c2bRegisterResponse: C2BRegisterResponse? = null,
    val c2bSimulateResponse: C2BSimulateResponse? = null,
    val b2cResponse: B2CResponse? = null,
    val dynamicQRResponse: DynamicQRResponse? = null,
    val accountBalanceResponse: AccountBalanceResponse? = null,
    val transactionStatusResponse: TransactionStatusResponse? = null,
    val reversalResponse: ReversalResponse? = null,
    val taxRemittanceResponse: TaxRemittanceResponse? = null,
    val businessPayBillResponse: BusinessPayBillResponse? = null,
    val businessBuyGoodsResponse: BusinessBuyGoodsResponse? = null,
    val billManagerResponse: BillManagerResponse? = null,
    val b2bExpressCheckOutResponse: B2BExpressCheckOutResponse? = null,
    val b2cAccountTopUpResponse: B2CAccountTopUpResponse? = null,
    val mpesaRatibaResponse: MpesaRatibaResponse? = null
)

class NewMpesaViewModel : ViewModel() {
    
    private val darajaApiCallService = DarajaApiCallService(
        consumerKey = "xkS5JzqHgNItCXl29G9PWqdQqAH5Tb2cVxU1pi83GFHHtGSZ",
        consumerSecret = "7Xo6rVHVdQxXfnU8sSR77Af0ibU2RaPJGXAhouaGHA3dnuq1e1seZKSt5b25bOpg"
    )
    
    private val _uiState = MutableStateFlow(NewMpesaUiState())
    val uiState: StateFlow<NewMpesaUiState> = _uiState.asStateFlow()
    
    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            error = null,
            success = null
        )
    }
    
    // Helper functions for STK operations
    private fun getTimestamp(): String {
        return Clock.System.now().getDarajaTimestamp()
    }
    
    private fun generatePassword(businessShortCode: String, passKey: String): String {
        val timestamp = getTimestamp()
        val password = "$businessShortCode$passKey$timestamp"
        return password.encodeBase64()
    }
    
    // STK Push - Customer Pay Bill Online
    fun initiateSTKPush(
        businessShortCode: String,
        passKey: String,
        amount: Int,
        phoneNumber: String,
        callBackURL: String,
        accountReference: String,
        transactionDesc: String = "Payment"
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val timestamp = getTimestamp()
            val password = generatePassword(businessShortCode, passKey)
            
            val result = darajaApiCallService.stkPush(
                STKPushRequest(
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
            )
            
            when (result) {
                is DarajaResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "STK Push initiated successfully. Check your phone for M-Pesa prompt.",
                        stkPushResponse = result.data
                    )
                }
                is DarajaResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.error
                    )
                }
            }
        }
    }
    
    // STK Query - Check payment status
    fun querySTKStatus(
        businessShortCode: String,
        passKey: String,
        checkoutRequestID: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val timestamp = getTimestamp()
            val password = generatePassword(businessShortCode, passKey)
            
            val result = darajaApiCallService.stkQuery(
                STKQueryRequest(
                    businessShortCode = businessShortCode,
                    password = password,
                    timestamp = timestamp,
                    checkoutRequestID = checkoutRequestID
                )
            )
            
            when (result) {
                is DarajaResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "STK Query completed successfully",
                        stkQueryResponse = result.data
                    )
                }
                is DarajaResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.error
                    )
                }
            }
        }
    }
    
    // C2B Register URL
    fun registerC2BURL(
        shortCode: String,
        responseType: String = "Completed",
        confirmationURL: String,
        validationURL: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val result = darajaApiCallService.c2bRegisterURL(
                C2BRegisterRequest(
                    shortCode = shortCode,
                    responseType = responseType,
                    confirmationURL = confirmationURL,
                    validationURL = validationURL
                )
            )
            
            when (result) {
                is DarajaResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "C2B URL registered successfully",
                        c2bRegisterResponse = result.data
                    )
                }
                is DarajaResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.error
                    )
                }
            }
        }
    }
    
    // C2B Simulate Transaction
    fun simulateC2B(
        shortCode: String,
        commandID: String,
        amount: Int,
        msisdn: String,
        billRefNumber: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val result = darajaApiCallService.c2bSimulate(
                C2BSimulateRequest(
                    shortCode = shortCode,
                    commandID = commandID,
                    amount = amount,
                    msisdn = msisdn,
                    billRefNumber = billRefNumber
                )
            )
            
            when (result) {
                is DarajaResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "C2B transaction simulated successfully",
                        c2bSimulateResponse = result.data
                    )
                }
                is DarajaResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.error
                    )
                }
            }
        }
    }
    
    // B2C - Business to Customer
    fun initiateB2C(
        initiatorName: String,
        securityCredential: String,
        commandID: String,
        amount: Int,
        partyA: String,
        partyB: String,
        remarks: String,
        queueTimeOutURL: String,
        resultURL: String,
        occasion: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val result = darajaApiCallService.b2cTransfer(
                B2CRequest(
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
            )
            
            when (result) {
                is DarajaResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "B2C payment initiated successfully",
                        b2cResponse = result.data
                    )
                }
                is DarajaResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.error
                    )
                }
            }
        }
    }
    
    // Dynamic QR - Generate QR Code for payments
    fun generateDynamicQR(
        merchantName: String,
        refNo: String,
        amount: Int,
        trxCode: String,
        cpi: String,
        size: String = "300"
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val result = darajaApiCallService.generateDynamicQR(
                DynamicQRRequest(
                    merchantName = merchantName,
                    refNo = refNo,
                    amount = amount,
                    trxCode = trxCode,
                    cpi = cpi,
                    size = size
                )
            )
            
            when (result) {
                is DarajaResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "Dynamic QR generated successfully",
                        dynamicQRResponse = result.data
                    )
                }
                is DarajaResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.error
                    )
                }
            }
        }
    }
    
    // Account Balance
    fun checkAccountBalance(
        initiator: String,
        securityCredential: String,
        commandID: String,
        partyA: String,
        identifierType: Int,
        remarks: String,
        queueTimeOutURL: String,
        resultURL: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val result = darajaApiCallService.accountBalance(
                AccountBalanceRequest(
                    initiator = initiator,
                    securityCredential = securityCredential,
                    commandID = commandID,
                    partyA = partyA,
                    identifierType = identifierType,
                    remarks = remarks,
                    queueTimeOutURL = queueTimeOutURL,
                    resultURL = resultURL
                )
            )
            
            when (result) {
                is DarajaResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "Account balance query initiated successfully",
                        accountBalanceResponse = result.data
                    )
                }
                is DarajaResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.error
                    )
                }
            }
        }
    }
    
    // Transaction Status
    fun queryTransactionStatus(
        initiator: String,
        securityCredential: String,
        commandID: String,
        transactionID: String,
        partyA: String,
        identifierType: Int,
        resultURL: String,
        queueTimeOutURL: String,
        remarks: String,
        occasion: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val result = darajaApiCallService.transactionStatus(
                TransactionStatusRequest(
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
            )
            
            when (result) {
                is DarajaResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "Transaction status queried successfully",
                        transactionStatusResponse = result.data
                    )
                }
                is DarajaResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.error
                    )
                }
            }
        }
    }
    
    // Reversal
    fun reverseTransaction(
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
        occasion: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val result = darajaApiCallService.reversal(
                ReversalRequest(
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
            )
            
            when (result) {
                is DarajaResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "Transaction reversal initiated successfully",
                        reversalResponse = result.data
                    )
                }
                is DarajaResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.error
                    )
                }
            }
        }
    }
    
    // Tax Remittance
    fun remitTax(
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
        occasion: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val result = darajaApiCallService.taxRemittance(
                TaxRemittanceRequest(
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
            )
            
            when (result) {
                is DarajaResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "Tax remittance initiated successfully",
                        taxRemittanceResponse = result.data
                    )
                }
                is DarajaResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.error
                    )
                }
            }
        }
    }
    
    // Business Pay Bill
    fun processBusinessPayBill(
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
        occasion: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val result = darajaApiCallService.businessPayBill(
                BusinessPayBillRequest(
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
            )
            
            when (result) {
                is DarajaResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "Business Pay Bill processed successfully",
                        businessPayBillResponse = result.data
                    )
                }
                is DarajaResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.error
                    )
                }
            }
        }
    }
    
    // Business Buy Goods
    fun processBusinessBuyGoods(
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
        occasion: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val result = darajaApiCallService.businessBuyGoods(
                BusinessBuyGoodsRequest(
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
            )
            
            when (result) {
                is DarajaResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "Business Buy Goods processed successfully",
                        businessBuyGoodsResponse = result.data
                    )
                }
                is DarajaResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.error
                    )
                }
            }
        }
    }
    
    // Bill Manager
    fun processBillManager(
        shortcode: String,
        email: String,
        officialContact: String,
        sendReminders: String,
        logo: String,
        callbackurl: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val result = darajaApiCallService.billManager(
                BillManagerRequest(
                    shortcode = shortcode,
                    email = email,
                    officialContact = officialContact,
                    sendReminders = sendReminders,
                    logo = logo,
                    callbackurl = callbackurl
                )
            )
            
            when (result) {
                is DarajaResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "Bill Manager processed successfully",
                        billManagerResponse = result.data
                    )
                }
                is DarajaResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.error
                    )
                }
            }
        }
    }
    
    // B2B Express CheckOut
    fun processB2BExpressCheckOut(
        primaryShortCode: String,
        receiverShortCode: String,
        amount: String,
        paymentRef: String,
        callbackUrl: String,
        partnerName: String,
        requestRefID: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val result = darajaApiCallService.b2bExpressCheckOut(
                B2BExpressCheckOutRequest(
                    primaryShortCode = primaryShortCode,
                    receiverShortCode = receiverShortCode,
                    amount = amount,
                    paymentRef = paymentRef,
                    callbackUrl = callbackUrl,
                    partnerName = partnerName,
                    requestRefID = requestRefID
                )
            )
            
            when (result) {
                is DarajaResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "B2B Express CheckOut processed successfully",
                        b2bExpressCheckOutResponse = result.data
                    )
                }
                is DarajaResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.error
                    )
                }
            }
        }
    }
    
    // B2C Account Top Up
    fun processB2CAccountTopUp(
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
        occasion: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val result = darajaApiCallService.b2cAccountTopUp(
                B2CAccountTopUpRequest(
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
            )
            
            when (result) {
                is DarajaResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "B2C Account Top Up processed successfully",
                        b2cAccountTopUpResponse = result.data
                    )
                }
                is DarajaResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.error
                    )
                }
            }
        }
    }
    
    // M-Pesa Ratiba
    fun processMpesaRatiba(
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
        frequency: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val result = darajaApiCallService.mpesaRatiba(
                MpesaRatibaRequest(
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
            )
            
            when (result) {
                is DarajaResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "M-Pesa Ratiba processed successfully",
                        mpesaRatibaResponse = result.data
                    )
                }
                is DarajaResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.error
                    )
                }
            }
        }
    }
}