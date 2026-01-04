package com.chacha.darajacmp.domain.usecase

import com.chacha.darajacmp.domain.model.Result
import com.chacha.darajacmp.domain.repository.DarajaRepository

class QueryTransactionStatusUseCase(
    private val repository: DarajaRepository
) {
    suspend operator fun invoke(
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
        return repository.queryTransactionStatus(
            initiator, securityCredential, commandID, transactionID, partyA, identifierType,
            resultURL, queueTimeOutURL, remarks, occasion, clientId, clientSecret
        )
    }
}

