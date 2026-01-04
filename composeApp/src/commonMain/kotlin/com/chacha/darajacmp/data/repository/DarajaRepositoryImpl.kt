package com.chacha.darajacmp.data.repository

import com.chacha.darajacmp.data.remote.DarajaRemoteDataSource
import com.chacha.darajacmp.domain.repository.DarajaRepository
import com.chacha.darajacmp.domain.model.*

class DarajaRepositoryImpl(
    private val remoteDataSource: DarajaRemoteDataSource
) : DarajaRepository {
    
    override suspend fun initiateSTKPush(
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
    ): Result<STKPush> = remoteDataSource.initiateSTKPush(
        businessShortCode, password, timestamp, amount, phoneNumber,
        callBackURL, accountReference, transactionDesc, clientId, clientSecret
    )
    
    override suspend fun querySTKStatus(
        businessShortCode: String,
        password: String,
        timestamp: String,
        checkoutRequestID: String,
        clientId: String,
        clientSecret: String
    ): Result<STKQuery> = remoteDataSource.querySTKStatus(
        businessShortCode, password, timestamp, checkoutRequestID, clientId, clientSecret
    )
    
    override suspend fun registerC2BURL(
        shortCode: String,
        responseType: String,
        confirmationURL: String,
        validationURL: String,
        clientId: String,
        clientSecret: String
    ): Result<C2BRegister> = remoteDataSource.registerC2BURL(
        shortCode, responseType, confirmationURL, validationURL, clientId, clientSecret
    )
    
    override suspend fun simulateC2B(
        shortCode: String,
        commandID: String,
        amount: Int,
        msisdn: String,
        billRefNumber: String,
        clientId: String,
        clientSecret: String
    ): Result<C2BSimulate> = remoteDataSource.simulateC2B(
        shortCode, commandID, amount, msisdn, billRefNumber, clientId, clientSecret
    )
    
    override suspend fun initiateB2C(
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
    ): Result<B2C> = remoteDataSource.initiateB2C(
        initiatorName, securityCredential, commandID, amount, partyA, partyB,
        remarks, queueTimeOutURL, resultURL, occasion, clientId, clientSecret
    )
    
    override suspend fun generateDynamicQR(
        merchantName: String,
        refNo: String,
        amount: Int,
        trxCode: String,
        cpi: String,
        size: String,
        clientId: String,
        clientSecret: String
    ): Result<DynamicQR> = remoteDataSource.generateDynamicQR(
        merchantName, refNo, amount, trxCode, cpi, size, clientId, clientSecret
    )
    
    override suspend fun checkAccountBalance(
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
    ): Result<AccountBalance> = remoteDataSource.checkAccountBalance(
        initiator, securityCredential, commandID, partyA, identifierType,
        remarks, queueTimeOutURL, resultURL, clientId, clientSecret
    )
    
    override suspend fun queryTransactionStatus(
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
    ): Result<TransactionStatus> = remoteDataSource.queryTransactionStatus(
        initiator, securityCredential, commandID, transactionID, partyA, identifierType,
        resultURL, queueTimeOutURL, remarks, occasion, clientId, clientSecret
    )
    
    override suspend fun reverseTransaction(
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
    ): Result<Reversal> = remoteDataSource.reverseTransaction(
        initiator, securityCredential, commandID, transactionID, amount, receiverParty,
        recieverIdentifierType, resultURL, queueTimeOutURL, remarks, occasion, clientId, clientSecret
    )
    
    override suspend fun remitTax(
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
    ): Result<TaxRemittance> = remoteDataSource.remitTax(
        initiator, securityCredential, commandID, senderIdentifierType, receiverIdentifierType,
        amount, partyA, partyB, accountReference, remarks, queueTimeOutURL, resultURL,
        occasion, clientId, clientSecret
    )
    
    override suspend fun processBusinessPayBill(
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
    ): Result<BusinessPayBill> = remoteDataSource.processBusinessPayBill(
        initiator, securityCredential, commandID, senderIdentifierType, receiverIdentifierType,
        amount, partyA, partyB, accountReference, requester, remarks,
        queueTimeOutURL, resultURL, occasion, clientId, clientSecret
    )
    
    override suspend fun processBusinessBuyGoods(
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
    ): Result<BusinessBuyGoods> = remoteDataSource.processBusinessBuyGoods(
        initiator, securityCredential, commandID, senderIdentifierType, receiverIdentifierType,
        amount, partyA, partyB, accountReference, requester, remarks,
        queueTimeOutURL, resultURL, occasion, clientId, clientSecret
    )
    
    override suspend fun processBillManager(
        shortcode: String,
        email: String,
        officialContact: String,
        sendReminders: String,
        logo: String,
        callbackurl: String,
        clientId: String,
        clientSecret: String
    ): Result<BillManager> = remoteDataSource.processBillManager(
        shortcode, email, officialContact, sendReminders, logo, callbackurl, clientId, clientSecret
    )
    
    override suspend fun processB2BExpressCheckOut(
        primaryShortCode: String,
        receiverShortCode: String,
        amount: String,
        paymentRef: String,
        callbackUrl: String,
        partnerName: String,
        requestRefID: String,
        clientId: String,
        clientSecret: String
    ): Result<B2BExpressCheckOut> = remoteDataSource.processB2BExpressCheckOut(
        primaryShortCode, receiverShortCode, amount, paymentRef, callbackUrl,
        partnerName, requestRefID, clientId, clientSecret
    )
    
    override suspend fun processB2CAccountTopUp(
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
    ): Result<B2CAccountTopUp> = remoteDataSource.processB2CAccountTopUp(
        initiator, securityCredential, commandID, senderIdentifierType, receiverIdentifierType,
        amount, partyA, partyB, accountReference, requester, remarks,
        queueTimeOutURL, resultURL, occasion, clientId, clientSecret
    )
    
    override suspend fun processMpesaRatiba(
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
    ): Result<MpesaRatiba> = remoteDataSource.processMpesaRatiba(
        standingOrderName, startDate, endDate, businessShortCode, transactionType,
        receiverPartyIdentifierType, amount, partyA, callBackURL, accountReference,
        transactionDesc, frequency, clientId, clientSecret
    )
    
}

