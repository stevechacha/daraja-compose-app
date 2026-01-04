package com.chacha.darajacmp.domain.usecase

import com.chacha.darajacmp.domain.model.Result
import com.chacha.darajacmp.domain.repository.DarajaRepository

class RegisterC2BURLUseCase(
    private val repository: DarajaRepository
) {
    suspend operator fun invoke(
        shortCode: String,
        responseType: String,
        confirmationURL: String,
        validationURL: String,
        clientId: String,
        clientSecret: String
    ): Result<com.chacha.darajacmp.domain.model.C2BRegister> {
        return repository.registerC2BURL(
            shortCode, responseType, confirmationURL, validationURL, clientId, clientSecret
        )
    }
}

