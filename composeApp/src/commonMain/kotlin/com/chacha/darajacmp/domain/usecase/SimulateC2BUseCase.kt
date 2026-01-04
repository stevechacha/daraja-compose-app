package com.chacha.darajacmp.domain.usecase

import com.chacha.darajacmp.domain.model.Result
import com.chacha.darajacmp.domain.repository.DarajaRepository

class SimulateC2BUseCase(
    private val repository: DarajaRepository
) {
    suspend operator fun invoke(
        shortCode: String,
        commandID: String,
        amount: Int,
        msisdn: String,
        billRefNumber: String,
        clientId: String,
        clientSecret: String
    ): Result<com.chacha.darajacmp.domain.model.C2BSimulate> {
        return repository.simulateC2B(
            shortCode, commandID, amount, msisdn, billRefNumber, clientId, clientSecret
        )
    }
}

