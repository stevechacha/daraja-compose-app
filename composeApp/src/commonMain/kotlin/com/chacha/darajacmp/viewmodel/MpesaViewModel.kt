package com.chacha.darajacmp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chacha.darajacmp.models.C2BRegisterRequest
import com.chacha.darajacmp.network.AuthService
import com.chacha.darajacmp.network.DarajaApiCallService
import com.chacha.darajacmp.network.DarajaApiService
import com.chacha.darajacmp.utils.DarajaResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MpesaViewModel : ViewModel() {
    private val authService = AuthService()
    private val  darajaApiCallService = DarajaApiCallService(
        consumerKey = "xkS5JzqHgNItCXl29G9PWqdQqAH5Tb2cVxU1pi83GFHHtGSZ",
        consumerSecret = "7Xo6rVHVdQxXfnU8sSR77Af0ibU2RaPJGXAhouaGHA3dnuq1e1seZKSt5b25bOpg"
    )

    private val darajaApiService = DarajaApiService(authService,darajaApiCallService)
    
    private val _uiState = MutableStateFlow(MpesaUiState())
    val uiState: StateFlow<MpesaUiState> = _uiState.asStateFlow()
    
    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            error = null,
            success = null
        )
    }
    
    // STK Push - Customer Pay Bill Online
    fun initiateSTKPush(
        clientId: String = "xkS5JzqHgNItCXl29G9PWqdQqAH5Tb2cVxU1pi83GFHHtGSZ",
        clientSecret: String = "7Xo6rVHVdQxXfnU8sSR77Af0ibU2RaPJGXAhouaGHA3dnuq1e1seZKSt5b25bOpg",
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

            
            try {
                val timestamp = darajaApiService.getTimestamp()
                val password = darajaApiService.generatePassword(businessShortCode, passKey)
                
                val result = darajaApiService.stkPush(
                    businessShortCode = businessShortCode,
                    password = password,
                    timestamp = timestamp,
                    amount = amount,
                    phoneNumber = phoneNumber,
                    callBackURL = callBackURL,
                    accountReference = accountReference,
                    transactionDesc = transactionDesc,
                    clientId = clientId,
                    clientSecret = clientSecret
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
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to initiate STK Push: ${e.message}"
                )
            }
        }
    }
    
    // STK Query - Check payment status
    fun querySTKStatus(
        clientId: String,
        clientSecret: String,
        businessShortCode: String,
        passKey: String,
        checkoutRequestID: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val timestamp = darajaApiService.getTimestamp()
                val password = darajaApiService.generatePassword(businessShortCode, passKey)
                
                val result = darajaApiService.stkQuery(
                    businessShortCode = businessShortCode,
                    password = password,
                    timestamp = timestamp,
                    checkoutRequestID = checkoutRequestID,
                    clientId = clientId,
                    clientSecret = clientSecret
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
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to query STK status: ${e.message}"
                )
            }
        }
    }
    
    // C2B Register URL
    fun registerC2BURL(
        clientId: String,
        clientSecret: String,
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
        clientId: String,
        clientSecret: String,
        shortCode: String,
        commandID: String,
        amount: Int,
        msisdn: String,
        billRefNumber: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val result = darajaApiService.c2bSimulate(
                    shortCode = shortCode,
                    commandID = commandID,
                    amount = amount,
                    msisdn = msisdn,
                    billRefNumber = billRefNumber,
                    clientId = clientId,
                    clientSecret = clientSecret
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
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to simulate C2B transaction: ${e.message}"
                )
            }
        }
    }
    
    // B2C - Business to Customer
    fun initiateB2C(
        clientId: String,
        clientSecret: String,
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
            
            try {
                val result = darajaApiService.b2cTransfer(
                    initiatorName = initiatorName,
                    securityCredential = securityCredential,
                    commandID = commandID,
                    amount = amount,
                    partyA = partyA,
                    partyB = partyB,
                    remarks = remarks,
                    queueTimeOutURL = queueTimeOutURL,
                    resultURL = resultURL,
                    occasion = occasion,
                    clientId = clientId,
                    clientSecret = clientSecret
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
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to initiate B2C payment: ${e.message}"
                )
            }
        }
    }
    
    // Dynamic QR - Generate QR Code for payments
    fun generateDynamicQR(
        clientId: String,
        clientSecret: String,
        merchantName: String,
        refNo: String,
        amount: Int,
        trxCode: String,
        cpi: String,
        size: String = "300"
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val result = darajaApiService.generateDynamicQR(
                    merchantName = merchantName,
                    refNo = refNo,
                    amount = amount,
                    trxCode = trxCode,
                    cpi = cpi,
                    size = size,
                    clientId = clientId,
                    clientSecret = clientSecret
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
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to generate Dynamic QR: ${e.message}"
                )
            }
        }
    }
    
    // Account Balance
    fun checkAccountBalance(
        clientId: String,
        clientSecret: String,
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
            
            try {
                val result = darajaApiService.accountBalance(
                    initiator = initiator,
                    securityCredential = securityCredential,
                    commandID = commandID,
                    partyA = partyA,
                    identifierType = identifierType,
                    remarks = remarks,
                    queueTimeOutURL = queueTimeOutURL,
                    resultURL = resultURL,
                    clientId = clientId,
                    clientSecret = clientSecret
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
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to check account balance: ${e.message}"
                )
            }
        }
    }
    
    // Transaction Status
    fun queryTransactionStatus(
        clientId: String,
        clientSecret: String,
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
            
            try {
                val result = darajaApiService.transactionStatus(
                    initiator = initiator,
                    securityCredential = securityCredential,
                    commandID = commandID,
                    transactionID = transactionID,
                    partyA = partyA,
                    identifierType = identifierType,
                    resultURL = resultURL,
                    queueTimeOutURL = queueTimeOutURL,
                    remarks = remarks,
                    occasion = occasion,
                    clientId = clientId,
                    clientSecret = clientSecret
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
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to query transaction status: ${e.message}"
                )
            }
        }
    }
    
    // Reversal
    fun reverseTransaction(
        clientId: String,
        clientSecret: String,
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
            
            try {
                val result = darajaApiService.reversal(
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
                    occasion = occasion,
                    clientId = clientId,
                    clientSecret = clientSecret
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
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to reverse transaction: ${e.message}"
                )
            }
        }
    }
    
    // Tax Remittance
    fun remitTax(
        clientId: String,
        clientSecret: String,
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
            
            try {
                val result = darajaApiService.taxRemittance(
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
                    resultURL = resultURL,
                    clientId = clientId,
                    clientSecret = clientSecret
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
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to remit tax: ${e.message}"
                )
            }
        }
    }
    
    // Business Pay Bill
    fun processBusinessPayBill(
        clientId: String,
        clientSecret: String,
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
            
            try {
                val result = darajaApiService.businessPayBill(
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
                    resultURL = resultURL,
                    clientId = clientId,
                    clientSecret = clientSecret
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
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to process business pay bill: ${e.message}"
                )
            }
        }
    }
    
    fun processBusinessBuyGoods(
        clientId: String,
        clientSecret: String,
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
            
            try {
                val result = darajaApiService.businessBuyGoods(
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
                    resultURL = resultURL,
                    clientId = clientId,
                    clientSecret = clientSecret
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
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to process business buy goods: ${e.message}"
                )
            }
        }
    }
    
    fun processBillManager(
        clientId: String,
        clientSecret: String,
        shortcode: String,
        email: String,
        officialContact: String,
        sendReminders: String,
        logo: String,
        callbackurl: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val result = darajaApiService.billManager(
                    shortcode = shortcode,
                    email = email,
                    officialContact = officialContact,
                    sendReminders = sendReminders,
                    logo = logo,
                    callbackurl = callbackurl,
                    clientId = clientId,
                    clientSecret = clientSecret
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
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to process bill manager: ${e.message}"
                )
            }
        }
    }
    
    fun processB2BExpressCheckOut(
        clientId: String,
        clientSecret: String,
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
            
            try {
                val result = darajaApiService.b2bExpressCheckOut(
                    primaryShortCode = primaryShortCode,
                    receiverShortCode = receiverShortCode,
                    amount = amount,
                    paymentRef = paymentRef,
                    callbackUrl = callbackUrl,
                    partnerName = partnerName,
                    requestRefID = requestRefID,
                    clientId = clientId,
                    clientSecret = clientSecret
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
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to process B2B Express CheckOut: ${e.message}"
                )
            }
        }
    }
    
    fun processB2CAccountTopUp(
        clientId: String,
        clientSecret: String,
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
            
            try {
                val result = darajaApiService.b2cAccountTopUp(
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
                    resultURL = resultURL,
                    clientId = clientId,
                    clientSecret = clientSecret
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
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to process B2C Account Top Up: ${e.message}"
                )
            }
        }
    }
    
    fun processMpesaRatiba(
        clientId: String,
        clientSecret: String,
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
            
            try {
                val result = darajaApiService.mpesaRatiba(
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
                    frequency = frequency,
                    clientId = clientId,
                    clientSecret = clientSecret
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
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to process M-Pesa Ratiba: ${e.message}"
                )
            }
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        darajaApiService.close()
    }
}
