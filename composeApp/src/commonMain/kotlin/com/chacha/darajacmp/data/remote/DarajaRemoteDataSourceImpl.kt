package com.chacha.darajacmp.data.remote

import com.chacha.darajacmp.domain.model.Result
import com.chacha.darajacmp.data.network.AuthService
import com.chacha.darajacmp.data.network.DarajaApiCallService
import com.chacha.darajacmp.data.network.DarajaApiService
import com.chacha.darajacmp.utils.DarajaResult

class DarajaRemoteDataSourceImpl(
    private val authService: AuthService,
    private val darajaApiCallService: DarajaApiCallService
) : DarajaRemoteDataSource {
    
    private val apiService = DarajaApiService(authService, darajaApiCallService)
    
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
    ): Result<com.chacha.darajacmp.domain.model.STKPush> {
        return when (val result = apiService.stkPush(
            businessShortCode, password, timestamp, amount, phoneNumber,
            callBackURL, accountReference, transactionDesc, clientId, clientSecret
        )) {
            is DarajaResult.Success -> Result.Success(result.data.toDomain())
            is DarajaResult.Error -> Result.Error(result.error)
        }
    }
    
    override suspend fun querySTKStatus(
        businessShortCode: String,
        password: String,
        timestamp: String,
        checkoutRequestID: String,
        clientId: String,
        clientSecret: String
    ): Result<com.chacha.darajacmp.domain.model.STKQuery> {
        return when (val result = apiService.stkQuery(
            businessShortCode, password, timestamp, checkoutRequestID, clientId, clientSecret
        )) {
            is DarajaResult.Success -> Result.Success(result.data.toDomain())
            is DarajaResult.Error -> Result.Error(result.error)
        }
    }
    
    override suspend fun registerC2BURL(
        shortCode: String,
        responseType: String,
        confirmationURL: String,
        validationURL: String,
        clientId: String,
        clientSecret: String
    ): Result<com.chacha.darajacmp.domain.model.C2BRegister> {
        return when (val result = apiService.c2bRegisterURL(
            shortCode, responseType, confirmationURL, validationURL, clientId, clientSecret
        )) {
            is DarajaResult.Success -> Result.Success(result.data.toDomain())
            is DarajaResult.Error -> Result.Error(result.error)
        }
    }
    
    override suspend fun simulateC2B(
        shortCode: String,
        commandID: String,
        amount: Int,
        msisdn: String,
        billRefNumber: String,
        clientId: String,
        clientSecret: String
    ): Result<com.chacha.darajacmp.domain.model.C2BSimulate> {
        return when (val result = apiService.c2bSimulate(
            shortCode, commandID, amount, msisdn, billRefNumber, clientId, clientSecret
        )) {
            is DarajaResult.Success -> Result.Success(result.data.toDomain())
            is DarajaResult.Error -> Result.Error(result.error)
        }
    }
    
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
    ): Result<com.chacha.darajacmp.domain.model.B2C> {
        return when (val result = apiService.b2cTransfer(
            initiatorName, securityCredential, commandID, amount, partyA, partyB,
            remarks, queueTimeOutURL, resultURL, occasion, clientId, clientSecret
        )) {
            is DarajaResult.Success -> Result.Success(result.data.toDomain())
            is DarajaResult.Error -> Result.Error(result.error)
        }
    }
    
    override suspend fun generateDynamicQR(
        merchantName: String,
        refNo: String,
        amount: Int,
        trxCode: String,
        cpi: String,
        size: String,
        clientId: String,
        clientSecret: String
    ): Result<com.chacha.darajacmp.domain.model.DynamicQR> {
        return when (val result = apiService.generateDynamicQR(
            merchantName, refNo, amount, trxCode, cpi, size, clientId, clientSecret
        )) {
            is DarajaResult.Success -> Result.Success(result.data.toDomain())
            is DarajaResult.Error -> Result.Error(result.error)
        }
    }
    
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
    ): Result<com.chacha.darajacmp.domain.model.AccountBalance> {
        return when (val result = apiService.accountBalance(
            initiator, securityCredential, commandID, partyA, identifierType,
            remarks, queueTimeOutURL, resultURL, clientId, clientSecret
        )) {
            is DarajaResult.Success -> Result.Success(result.data.toDomain())
            is DarajaResult.Error -> Result.Error(result.error)
        }
    }
    
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
    ): Result<com.chacha.darajacmp.domain.model.TransactionStatus> {
        return when (val result = apiService.transactionStatus(
            initiator, securityCredential, commandID, transactionID, partyA, identifierType,
            resultURL, queueTimeOutURL, remarks, occasion, clientId, clientSecret
        )) {
            is DarajaResult.Success -> Result.Success(result.data.toDomain())
            is DarajaResult.Error -> Result.Error(result.error)
        }
    }
    
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
    ): Result<com.chacha.darajacmp.domain.model.Reversal> {
        return when (val result = apiService.reversal(
            initiator, securityCredential, commandID, transactionID, amount, receiverParty,
            recieverIdentifierType, resultURL, queueTimeOutURL, remarks, occasion, clientId, clientSecret
        )) {
            is DarajaResult.Success -> Result.Success(result.data.toDomain())
            is DarajaResult.Error -> Result.Error(result.error)
        }
    }
    
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
    ): Result<com.chacha.darajacmp.domain.model.TaxRemittance> {
        return when (val result = apiService.taxRemittance(
            initiator, securityCredential, commandID, senderIdentifierType, receiverIdentifierType,
            amount, partyA, partyB, accountReference, remarks, queueTimeOutURL, resultURL,
            clientId, clientSecret
        )) {
            is DarajaResult.Success -> Result.Success(result.data.toDomain())
            is DarajaResult.Error -> Result.Error(result.error)
        }
    }
    
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
    ): Result<com.chacha.darajacmp.domain.model.BusinessPayBill> {
        return when (val result = apiService.businessPayBill(
            initiator, securityCredential, commandID, senderIdentifierType, receiverIdentifierType,
            amount, partyA, partyB, accountReference, requester, remarks,
            queueTimeOutURL, resultURL, clientId, clientSecret
        )) {
            is DarajaResult.Success -> Result.Success(result.data.toDomain())
            is DarajaResult.Error -> Result.Error(result.error)
        }
    }
    
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
    ): Result<com.chacha.darajacmp.domain.model.BusinessBuyGoods> {
        return when (val result = apiService.businessBuyGoods(
            initiator, securityCredential, commandID, senderIdentifierType, receiverIdentifierType,
            amount, partyA, partyB, accountReference, requester, remarks,
            queueTimeOutURL, resultURL, clientId, clientSecret
        )) {
            is DarajaResult.Success -> Result.Success(result.data.toDomain())
            is DarajaResult.Error -> Result.Error(result.error)
        }
    }
    
    override suspend fun processBillManager(
        shortcode: String,
        email: String,
        officialContact: String,
        sendReminders: String,
        logo: String,
        callbackurl: String,
        clientId: String,
        clientSecret: String
    ): Result<com.chacha.darajacmp.domain.model.BillManager> {
        return when (val result = apiService.billManager(
            shortcode, email, officialContact, sendReminders, logo, callbackurl, clientId, clientSecret
        )) {
            is DarajaResult.Success -> Result.Success(result.data.toDomain())
            is DarajaResult.Error -> Result.Error(result.error)
        }
    }
    
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
    ): Result<com.chacha.darajacmp.domain.model.B2BExpressCheckOut> {
        return when (val result = apiService.b2bExpressCheckOut(
            primaryShortCode, receiverShortCode, amount, paymentRef, callbackUrl,
            partnerName, requestRefID, clientId, clientSecret
        )) {
            is DarajaResult.Success -> Result.Success(result.data.toDomain())
            is DarajaResult.Error -> Result.Error(result.error)
        }
    }
    
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
    ): Result<com.chacha.darajacmp.domain.model.B2CAccountTopUp> {
        return when (val result = apiService.b2cAccountTopUp(
            initiator, securityCredential, commandID, senderIdentifierType, receiverIdentifierType,
            amount, partyA, partyB, accountReference, requester, remarks,
            queueTimeOutURL, resultURL, clientId, clientSecret
        )) {
            is DarajaResult.Success -> Result.Success(result.data.toDomain())
            is DarajaResult.Error -> Result.Error(result.error)
        }
    }
    
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
    ): Result<com.chacha.darajacmp.domain.model.MpesaRatiba> {
        return when (val result = apiService.mpesaRatiba(
            standingOrderName, startDate, endDate, businessShortCode, transactionType,
            receiverPartyIdentifierType, amount, partyA, callBackURL, accountReference,
            transactionDesc, frequency, clientId, clientSecret
        )) {
            is DarajaResult.Success -> Result.Success(result.data.toDomain())
            is DarajaResult.Error -> Result.Error(result.error)
        }
    }
    
}

// Extension functions to convert DTOs to domain models
private fun com.chacha.darajacmp.data.dto.response.STKPushResponse.toDomain(): com.chacha.darajacmp.domain.model.STKPush {
    return com.chacha.darajacmp.domain.model.STKPush(
        merchantRequestID = merchantRequestID,
        checkoutRequestID = checkoutRequestID,
        responseCode = responseCode,
        responseDescription = responseDescription,
        customerMessage = customerMessage
    )
}

private fun com.chacha.darajacmp.data.dto.response.STKQueryResponse.toDomain(): com.chacha.darajacmp.domain.model.STKQuery {
    return com.chacha.darajacmp.domain.model.STKQuery(
        responseCode = responseCode ?: "",
        responseDescription = responseDescription ?: "",
        merchantRequestID = merchantRequestID,
        checkoutRequestID = checkoutRequestID,
        resultCode = resultCode,
        resultDesc = resultDesc
    )
}

private fun com.chacha.darajacmp.data.dto.response.C2BRegisterResponse.toDomain(): com.chacha.darajacmp.domain.model.C2BRegister {
    return com.chacha.darajacmp.domain.model.C2BRegister(
        conversationID = conversationID ?: "",
        originatorConversationID = originatorConversationID ?: "",
        responseCode = responseCode ?: "",
        responseDescription = responseDescription ?: "",
        requestId = requestId,
        errorCode = errorCode,
        errorMessage = errorMessage
    )
}

private fun com.chacha.darajacmp.data.dto.response.C2BSimulateResponse.toDomain(): com.chacha.darajacmp.domain.model.C2BSimulate {
    return com.chacha.darajacmp.domain.model.C2BSimulate(
        conversationID = conversationID,
        originatorConversationID = originatorConversationID,
        responseCode = responseCode,
        responseDescription = responseDescription
    )
}

private fun com.chacha.darajacmp.data.dto.response.B2CResponse.toDomain(): com.chacha.darajacmp.domain.model.B2C {
    return com.chacha.darajacmp.domain.model.B2C(
        conversationID = conversationID,
        originatorConversationID = originatorConversationID,
        responseCode = responseCode,
        responseDescription = responseDescription
    )
}

private fun com.chacha.darajacmp.data.dto.response.DynamicQRResponse.toDomain(): com.chacha.darajacmp.domain.model.DynamicQR {
    return com.chacha.darajacmp.domain.model.DynamicQR(
        responseCode = responseCode,
        responseDescription = responseDescription,
        qrCode = qrCode
    )
}

private fun com.chacha.darajacmp.data.dto.response.AccountBalanceResponse.toDomain(): com.chacha.darajacmp.domain.model.AccountBalance {
    return com.chacha.darajacmp.domain.model.AccountBalance(
        conversationID = conversationID,
        originatorConversationID = originatorConversationID,
        responseCode = responseCode,
        responseDescription = responseDescription,
        requestId = requestId,
        errorCode = errorCode,
        errorMessage = errorMessage
    )
}

private fun com.chacha.darajacmp.data.dto.response.TransactionStatusResponse.toDomain(): com.chacha.darajacmp.domain.model.TransactionStatus {
    return com.chacha.darajacmp.domain.model.TransactionStatus(
        conversationID = conversationID,
        originatorConversationID = originatorConversationID,
        responseCode = responseCode,
        responseDescription = responseDescription
    )
}

private fun com.chacha.darajacmp.data.dto.response.ReversalResponse.toDomain(): com.chacha.darajacmp.domain.model.Reversal {
    return com.chacha.darajacmp.domain.model.Reversal(
        conversationID = conversationID,
        originatorConversationID = originatorConversationID,
        responseCode = responseCode,
        responseDescription = responseDescription,
        requestId = requestId,
        errorCode = errorCode,
        errorMessage = errorMessage
    )
}

private fun com.chacha.darajacmp.data.dto.response.TaxRemittanceResponse.toDomain(): com.chacha.darajacmp.domain.model.TaxRemittance {
    return com.chacha.darajacmp.domain.model.TaxRemittance(
        conversationID = conversationID,
        originatorConversationID = originatorConversationID,
        responseCode = responseCode,
        responseDescription = responseDescription,
        requestId = requestId,
        errorCode = errorCode,
        errorMessage = errorMessage
    )
}

private fun com.chacha.darajacmp.data.dto.response.BusinessPayBillResponse.toDomain(): com.chacha.darajacmp.domain.model.BusinessPayBill {
    return com.chacha.darajacmp.domain.model.BusinessPayBill(
        conversationID = conversationID,
        originatorConversationID = originatorConversationID,
        responseCode = responseCode,
        responseDescription = responseDescription,
        requestId = requestId,
        errorCode = errorCode,
        errorMessage = errorMessage
    )
}

private fun com.chacha.darajacmp.data.dto.response.BusinessBuyGoodsResponse.toDomain(): com.chacha.darajacmp.domain.model.BusinessBuyGoods {
    return com.chacha.darajacmp.domain.model.BusinessBuyGoods(
        conversationID = conversationID,
        originatorConversationID = originatorConversationID,
        responseCode = responseCode,
        responseDescription = responseDescription,
        requestId = requestId,
        errorCode = errorCode,
        errorMessage = errorMessage
    )
}

private fun com.chacha.darajacmp.data.dto.response.BillManagerResponse.toDomain(): com.chacha.darajacmp.domain.model.BillManager {
    return com.chacha.darajacmp.domain.model.BillManager(
        conversationID = conversationID,
        originatorConversationID = originatorConversationID,
        responseCode = responseCode,
        responseDescription = responseDescription,
        requestId = requestId,
        errorCode = errorCode,
        errorMessage = errorMessage
    )
}

private fun com.chacha.darajacmp.data.dto.response.B2BExpressCheckOutResponse.toDomain(): com.chacha.darajacmp.domain.model.B2BExpressCheckOut {
    return com.chacha.darajacmp.domain.model.B2BExpressCheckOut(
        conversationID = conversationID,
        originatorConversationID = originatorConversationID,
        responseCode = responseCode,
        responseDescription = responseDescription,
        requestId = requestId,
        errorCode = errorCode,
        errorMessage = errorMessage
    )
}

private fun com.chacha.darajacmp.data.dto.response.B2CAccountTopUpResponse.toDomain(): com.chacha.darajacmp.domain.model.B2CAccountTopUp {
    return com.chacha.darajacmp.domain.model.B2CAccountTopUp(
        conversationID = conversationID,
        originatorConversationID = originatorConversationID,
        responseCode = responseCode,
        responseDescription = responseDescription,
        requestId = requestId,
        errorCode = errorCode,
        errorMessage = errorMessage
    )
}

private fun com.chacha.darajacmp.data.dto.response.MpesaRatibaResponse.toDomain(): com.chacha.darajacmp.domain.model.MpesaRatiba {
    return com.chacha.darajacmp.domain.model.MpesaRatiba(
        conversationID = conversationID,
        originatorConversationID = originatorConversationID,
        responseCode = responseCode,
        responseDescription = responseDescription,
        requestId = requestId,
        errorCode = errorCode,
        errorMessage = errorMessage
    )
}

