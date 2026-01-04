package com.chacha.darajacmp.domain.usecase

import com.chacha.darajacmp.domain.model.Result
import com.chacha.darajacmp.domain.repository.DarajaRepository

class ReverseTransactionUseCase(
    private val repository: DarajaRepository
) {
    suspend operator fun invoke(
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
        return repository.reverseTransaction(
            initiator, securityCredential, commandID, transactionID, amount, receiverParty,
            recieverIdentifierType, resultURL, queueTimeOutURL, remarks, occasion, clientId, clientSecret
        )
    }
}

