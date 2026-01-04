package com.chacha.darajacmp.domain.usecase

import com.chacha.darajacmp.domain.model.Result
import com.chacha.darajacmp.domain.repository.DarajaRepository

class CheckAccountBalanceUseCase(
    private val repository: DarajaRepository
) {
    suspend operator fun invoke(
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
        return repository.checkAccountBalance(
            initiator, securityCredential, commandID, partyA, identifierType,
            remarks, queueTimeOutURL, resultURL, clientId, clientSecret
        )
    }
}

