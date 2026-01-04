package com.chacha.darajacmp.domain.usecase

import com.chacha.darajacmp.domain.model.Result
import com.chacha.darajacmp.domain.repository.DarajaRepository

class ProcessB2BExpressCheckOutUseCase(
    private val repository: DarajaRepository
) {
    suspend operator fun invoke(
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
        return repository.processB2BExpressCheckOut(
            primaryShortCode, receiverShortCode, amount, paymentRef, callbackUrl,
            partnerName, requestRefID, clientId, clientSecret
        )
    }
}

