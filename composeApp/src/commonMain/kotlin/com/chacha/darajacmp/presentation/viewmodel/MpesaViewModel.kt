package com.chacha.darajacmp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chacha.darajacmp.domain.model.*
import com.chacha.darajacmp.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MpesaUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: String? = null,
    val stkPushResponse: STKPush? = null,
    val stkQueryResponse: STKQuery? = null,
    val c2bRegisterResponse: C2BRegister? = null,
    val c2bSimulateResponse: C2BSimulate? = null,
    val b2cResponse: B2C? = null,
    val dynamicQRResponse: DynamicQR? = null,
    val accountBalanceResponse: AccountBalance? = null,
    val transactionStatusResponse: TransactionStatus? = null,
    val reversalResponse: Reversal? = null,
    val taxRemittanceResponse: TaxRemittance? = null,
    val businessPayBillResponse: BusinessPayBill? = null,
    val businessBuyGoodsResponse: BusinessBuyGoods? = null,
    val billManagerResponse: BillManager? = null,
    val b2bExpressCheckOutResponse: B2BExpressCheckOut? = null,
    val b2cAccountTopUpResponse: B2CAccountTopUp? = null,
    val mpesaRatibaResponse: MpesaRatiba? = null
)

/**
 * ViewModel for M-Pesa operations.
 * Follows Clean Architecture principles - only uses UseCases, never directly accesses Repository.
 */
class MpesaViewModel(
    private val initiateSTKPushUseCase: InitiateSTKPushUseCase,
    private val querySTKStatusUseCase: QuerySTKStatusUseCase,
    private val registerC2BURLUseCase: RegisterC2BURLUseCase,
    private val simulateC2BUseCase: SimulateC2BUseCase,
    private val initiateB2CUseCase: InitiateB2CUseCase,
    private val generateDynamicQRUseCase: GenerateDynamicQRUseCase,
    private val checkAccountBalanceUseCase: CheckAccountBalanceUseCase,
    private val queryTransactionStatusUseCase: QueryTransactionStatusUseCase,
    private val reverseTransactionUseCase: ReverseTransactionUseCase,
    private val remitTaxUseCase: RemitTaxUseCase,
    private val processBusinessPayBillUseCase: ProcessBusinessPayBillUseCase,
    private val processBusinessBuyGoodsUseCase: ProcessBusinessBuyGoodsUseCase,
    private val processBillManagerUseCase: ProcessBillManagerUseCase,
    private val processB2BExpressCheckOutUseCase: ProcessB2BExpressCheckOutUseCase,
    private val processB2CAccountTopUpUseCase: ProcessB2CAccountTopUpUseCase,
    private val processMpesaRatibaUseCase: ProcessMpesaRatibaUseCase
) : ViewModel() {
    
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
        businessShortCode: String,
        passKey: String,
        amount: Int,
        phoneNumber: String,
        callBackURL: String,
        accountReference: String,
        transactionDesc: String = "Payment",
        clientId: String,
        clientSecret: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = initiateSTKPushUseCase(
                businessShortCode, passKey, amount, phoneNumber,
                callBackURL, accountReference, transactionDesc, clientId, clientSecret
            )) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "STK Push initiated successfully. Check your phone for M-Pesa prompt.",
                        stkPushResponse = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
    
    // STK Query - Check payment status
    fun querySTKStatus(
        businessShortCode: String,
        passKey: String,
        checkoutRequestID: String,
        clientId: String,
        clientSecret: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = querySTKStatusUseCase(
                businessShortCode, passKey, checkoutRequestID, clientId, clientSecret
            )) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "STK Query completed successfully",
                        stkQueryResponse = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
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
        validationURL: String,
        clientId: String,
        clientSecret: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = registerC2BURLUseCase(
                shortCode, responseType, confirmationURL, validationURL, clientId, clientSecret
            )) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "C2B URL registered successfully",
                        c2bRegisterResponse = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
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
        billRefNumber: String,
        clientId: String,
        clientSecret: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = simulateC2BUseCase(
                shortCode, commandID, amount, msisdn, billRefNumber, clientId, clientSecret
            )) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "C2B transaction simulated successfully",
                        c2bSimulateResponse = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
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
        occasion: String,
        clientId: String,
        clientSecret: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = initiateB2CUseCase(
                initiatorName, securityCredential, commandID, amount, partyA, partyB,
                remarks, queueTimeOutURL, resultURL, occasion, clientId, clientSecret
            )) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "B2C payment initiated successfully",
                        b2cResponse = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
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
        size: String = "300",
        clientId: String,
        clientSecret: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = generateDynamicQRUseCase(
                merchantName, refNo, amount, trxCode, cpi, size, clientId, clientSecret
            )) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "Dynamic QR generated successfully",
                        dynamicQRResponse = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
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
        resultURL: String,
        clientId: String,
        clientSecret: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = checkAccountBalanceUseCase(
                initiator, securityCredential, commandID, partyA, identifierType,
                remarks, queueTimeOutURL, resultURL, clientId, clientSecret
            )) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "Account balance query initiated successfully",
                        accountBalanceResponse = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
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
        occasion: String,
        clientId: String,
        clientSecret: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = queryTransactionStatusUseCase(
                initiator, securityCredential, commandID, transactionID, partyA, identifierType,
                resultURL, queueTimeOutURL, remarks, occasion, clientId, clientSecret
            )) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "Transaction status queried successfully",
                        transactionStatusResponse = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
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
        occasion: String,
        clientId: String,
        clientSecret: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = reverseTransactionUseCase(
                initiator, securityCredential, commandID, transactionID, amount, receiverParty,
                recieverIdentifierType, resultURL, queueTimeOutURL, remarks, occasion, clientId, clientSecret
            )) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "Transaction reversal initiated successfully",
                        reversalResponse = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
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
        occasion: String,
        clientId: String,
        clientSecret: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = remitTaxUseCase(
                initiator, securityCredential, commandID, senderIdentifierType, receiverIdentifierType,
                amount, partyA, partyB, accountReference, remarks, queueTimeOutURL, resultURL,
                occasion, clientId, clientSecret
            )) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "Tax remittance initiated successfully",
                        taxRemittanceResponse = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
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
        occasion: String,
        clientId: String,
        clientSecret: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = processBusinessPayBillUseCase(
                initiator, securityCredential, commandID, senderIdentifierType, receiverIdentifierType,
                amount, partyA, partyB, accountReference, requester, remarks,
                queueTimeOutURL, resultURL, occasion, clientId, clientSecret
            )) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "Business Pay Bill processed successfully",
                        businessPayBillResponse = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
    
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
        occasion: String,
        clientId: String,
        clientSecret: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = processBusinessBuyGoodsUseCase(
                initiator, securityCredential, commandID, senderIdentifierType, receiverIdentifierType,
                amount, partyA, partyB, accountReference, requester, remarks,
                queueTimeOutURL, resultURL, occasion, clientId, clientSecret
            )) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "Business Buy Goods processed successfully",
                        businessBuyGoodsResponse = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
    
    fun processBillManager(
        shortcode: String,
        email: String,
        officialContact: String,
        sendReminders: String,
        logo: String,
        callbackurl: String,
        clientId: String,
        clientSecret: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = processBillManagerUseCase(
                shortcode, email, officialContact, sendReminders, logo, callbackurl, clientId, clientSecret
            )) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "Bill Manager processed successfully",
                        billManagerResponse = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
    
    fun processB2BExpressCheckOut(
        primaryShortCode: String,
        receiverShortCode: String,
        amount: String,
        paymentRef: String,
        callbackUrl: String,
        partnerName: String,
        requestRefID: String,
        clientId: String,
        clientSecret: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = processB2BExpressCheckOutUseCase(
                primaryShortCode, receiverShortCode, amount, paymentRef, callbackUrl,
                partnerName, requestRefID, clientId, clientSecret
            )) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "B2B Express CheckOut processed successfully",
                        b2bExpressCheckOutResponse = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
    
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
        occasion: String,
        clientId: String,
        clientSecret: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = processB2CAccountTopUpUseCase(
                initiator, securityCredential, commandID, senderIdentifierType, receiverIdentifierType,
                amount, partyA, partyB, accountReference, requester, remarks,
                queueTimeOutURL, resultURL, occasion, clientId, clientSecret
            )) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "B2C Account Top Up processed successfully",
                        b2cAccountTopUpResponse = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
    
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
        frequency: String,
        clientId: String,
        clientSecret: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = processMpesaRatibaUseCase(
                standingOrderName, startDate, endDate, businessShortCode, transactionType,
                receiverPartyIdentifierType, amount, partyA, callBackURL, accountReference,
                transactionDesc, frequency, clientId, clientSecret
            )) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        success = "M-Pesa Ratiba processed successfully",
                        mpesaRatibaResponse = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
}

