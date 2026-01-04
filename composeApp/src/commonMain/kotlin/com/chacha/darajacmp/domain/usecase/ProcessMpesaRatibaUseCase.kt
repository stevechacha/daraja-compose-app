package com.chacha.darajacmp.domain.usecase

import com.chacha.darajacmp.domain.model.Result
import com.chacha.darajacmp.domain.repository.DarajaRepository

class ProcessMpesaRatibaUseCase(
    private val repository: DarajaRepository
) {
    suspend operator fun invoke(
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
        return repository.processMpesaRatiba(
            standingOrderName, startDate, endDate, businessShortCode, transactionType,
            receiverPartyIdentifierType, amount, partyA, callBackURL, accountReference,
            transactionDesc, frequency, clientId, clientSecret
        )
    }
}

