package com.chacha.darajacmp.domain.usecase

import com.chacha.darajacmp.domain.model.Result
import com.chacha.darajacmp.domain.repository.DarajaRepository

class ProcessBillManagerUseCase(
    private val repository: DarajaRepository
) {
    suspend operator fun invoke(
        shortcode: String,
        email: String,
        officialContact: String,
        sendReminders: String,
        logo: String,
        callbackurl: String,
        clientId: String,
        clientSecret: String
    ): Result<com.chacha.darajacmp.domain.model.BillManager> {
        return repository.processBillManager(
            shortcode, email, officialContact, sendReminders, logo, callbackurl, clientId, clientSecret
        )
    }
}

