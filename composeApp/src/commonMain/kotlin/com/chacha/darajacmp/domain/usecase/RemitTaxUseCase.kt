package com.chacha.darajacmp.domain.usecase

import com.chacha.darajacmp.domain.model.Result
import com.chacha.darajacmp.domain.repository.DarajaRepository

class RemitTaxUseCase(
    private val repository: DarajaRepository
) {
    suspend operator fun invoke(
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
        return repository.remitTax(
            initiator, securityCredential, commandID, senderIdentifierType, receiverIdentifierType,
            amount, partyA, partyB, accountReference, remarks, queueTimeOutURL, resultURL,
            occasion, clientId, clientSecret
        )
    }
}

