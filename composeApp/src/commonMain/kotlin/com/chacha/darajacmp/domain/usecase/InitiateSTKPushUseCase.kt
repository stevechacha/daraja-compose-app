package com.chacha.darajacmp.domain.usecase

import com.chacha.darajacmp.domain.model.Result
import com.chacha.darajacmp.domain.repository.DarajaRepository
import com.chacha.darajacmp.domain.service.DarajaService

class InitiateSTKPushUseCase(
    private val repository: DarajaRepository,
    private val darajaService: DarajaService
) {
    suspend operator fun invoke(
        businessShortCode: String,
        passKey: String,
        amount: Int,
        phoneNumber: String,
        callBackURL: String,
        accountReference: String,
        transactionDesc: String = "Payment",
        clientId: String,
        clientSecret: String
    ): Result<com.chacha.darajacmp.domain.model.STKPush> {
        val timestamp = darajaService.getTimestamp()
        val password = darajaService.generatePassword(businessShortCode, passKey)
        
        return repository.initiateSTKPush(
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
    }
}

