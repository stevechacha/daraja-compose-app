package com.chacha.darajacmp.domain.usecase

import com.chacha.darajacmp.domain.model.Result
import com.chacha.darajacmp.domain.repository.DarajaRepository

class InitiateB2CUseCase(
    private val repository: DarajaRepository
) {
    suspend operator fun invoke(
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
        return repository.initiateB2C(
            initiatorName, securityCredential, commandID, amount, partyA, partyB,
            remarks, queueTimeOutURL, resultURL, occasion, clientId, clientSecret
        )
    }
}

