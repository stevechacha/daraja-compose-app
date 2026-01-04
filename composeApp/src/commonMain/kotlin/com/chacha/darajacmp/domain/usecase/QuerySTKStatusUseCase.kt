package com.chacha.darajacmp.domain.usecase

import com.chacha.darajacmp.domain.model.Result
import com.chacha.darajacmp.domain.repository.DarajaRepository
import com.chacha.darajacmp.domain.service.DarajaService

class QuerySTKStatusUseCase(
    private val repository: DarajaRepository,
    private val darajaService: DarajaService
) {
    suspend operator fun invoke(
        businessShortCode: String,
        passKey: String,
        checkoutRequestID: String,
        clientId: String,
        clientSecret: String
    ): Result<com.chacha.darajacmp.domain.model.STKQuery> {
        val timestamp = darajaService.getTimestamp()
        val password = darajaService.generatePassword(businessShortCode, passKey)
        
        return repository.querySTKStatus(
            businessShortCode = businessShortCode,
            password = password,
            timestamp = timestamp,
            checkoutRequestID = checkoutRequestID,
            clientId = clientId,
            clientSecret = clientSecret
        )
    }
}

