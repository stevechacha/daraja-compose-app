package com.chacha.darajacmp.data.remote

import com.chacha.darajacmp.domain.model.Result

interface DarajaRemoteDataSource {
    suspend fun initiateSTKPush(
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
    ): Result<com.chacha.darajacmp.domain.model.STKPush>
    
    suspend fun querySTKStatus(
        businessShortCode: String,
        password: String,
        timestamp: String,
        checkoutRequestID: String,
        clientId: String,
        clientSecret: String
    ): Result<com.chacha.darajacmp.domain.model.STKQuery>
    
    suspend fun registerC2BURL(
        shortCode: String,
        responseType: String,
        confirmationURL: String,
        validationURL: String,
        clientId: String,
        clientSecret: String
    ): Result<com.chacha.darajacmp.domain.model.C2BRegister>
    
    suspend fun simulateC2B(
        shortCode: String,
        commandID: String,
        amount: Int,
        msisdn: String,
        billRefNumber: String,
        clientId: String,
        clientSecret: String
    ): Result<com.chacha.darajacmp.domain.model.C2BSimulate>
    
    suspend fun initiateB2C(
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
    ): Result<com.chacha.darajacmp.domain.model.B2C>
    
    suspend fun generateDynamicQR(
        merchantName: String,
        refNo: String,
        amount: Int,
        trxCode: String,
        cpi: String,
        size: String,
        clientId: String,
        clientSecret: String
    ): Result<com.chacha.darajacmp.domain.model.DynamicQR>
    
    suspend fun checkAccountBalance(
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
    ): Result<com.chacha.darajacmp.domain.model.AccountBalance>
    
    suspend fun queryTransactionStatus(
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
    ): Result<com.chacha.darajacmp.domain.model.TransactionStatus>
    
    suspend fun reverseTransaction(
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
    ): Result<com.chacha.darajacmp.domain.model.Reversal>
    
    suspend fun remitTax(
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
    ): Result<com.chacha.darajacmp.domain.model.TaxRemittance>
    
    suspend fun processBusinessPayBill(
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
    ): Result<com.chacha.darajacmp.domain.model.BusinessPayBill>
    
    suspend fun processBusinessBuyGoods(
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
    ): Result<com.chacha.darajacmp.domain.model.BusinessBuyGoods>
    
    suspend fun processBillManager(
        shortcode: String,
        email: String,
        officialContact: String,
        sendReminders: String,
        logo: String,
        callbackurl: String,
        clientId: String,
        clientSecret: String
    ): Result<com.chacha.darajacmp.domain.model.BillManager>
    
    suspend fun processB2BExpressCheckOut(
        primaryShortCode: String,
        receiverShortCode: String,
        amount: String,
        paymentRef: String,
        callbackUrl: String,
        partnerName: String,
        requestRefID: String,
        clientId: String,
        clientSecret: String
    ): Result<com.chacha.darajacmp.domain.model.B2BExpressCheckOut>
    
    suspend fun processB2CAccountTopUp(
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
    ): Result<com.chacha.darajacmp.domain.model.B2CAccountTopUp>
    
    suspend fun processMpesaRatiba(
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
    ): Result<com.chacha.darajacmp.domain.model.MpesaRatiba>
    
}

