package com.chacha.darajacmp.domain.usecase

import com.chacha.darajacmp.domain.model.Result
import com.chacha.darajacmp.domain.repository.DarajaRepository

class GenerateDynamicQRUseCase(
    private val repository: DarajaRepository
) {
    suspend operator fun invoke(
        merchantName: String,
        refNo: String,
        amount: Int,
        trxCode: String,
        cpi: String,
        size: String,
        clientId: String,
        clientSecret: String
    ): Result<com.chacha.darajacmp.domain.model.DynamicQR> {
        return repository.generateDynamicQR(
            merchantName, refNo, amount, trxCode, cpi, size, clientId, clientSecret
        )
    }
}

