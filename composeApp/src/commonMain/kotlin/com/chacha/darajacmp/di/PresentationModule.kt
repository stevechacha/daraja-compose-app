package com.chacha.darajacmp.di

import com.chacha.darajacmp.domain.usecase.*
import com.chacha.darajacmp.presentation.viewmodel.MpesaViewModel
import org.koin.dsl.module

val presentationModule = module {
    factory {
        MpesaViewModel(
            initiateSTKPushUseCase = get<InitiateSTKPushUseCase>(),
            querySTKStatusUseCase = get<QuerySTKStatusUseCase>(),
            registerC2BURLUseCase = get<RegisterC2BURLUseCase>(),
            simulateC2BUseCase = get<SimulateC2BUseCase>(),
            initiateB2CUseCase = get<InitiateB2CUseCase>(),
            generateDynamicQRUseCase = get<GenerateDynamicQRUseCase>(),
            checkAccountBalanceUseCase = get<CheckAccountBalanceUseCase>(),
            queryTransactionStatusUseCase = get<QueryTransactionStatusUseCase>(),
            reverseTransactionUseCase = get<ReverseTransactionUseCase>(),
            remitTaxUseCase = get<RemitTaxUseCase>(),
            processBusinessPayBillUseCase = get<ProcessBusinessPayBillUseCase>(),
            processBusinessBuyGoodsUseCase = get<ProcessBusinessBuyGoodsUseCase>(),
            processBillManagerUseCase = get<ProcessBillManagerUseCase>(),
            processB2BExpressCheckOutUseCase = get<ProcessB2BExpressCheckOutUseCase>(),
            processB2CAccountTopUpUseCase = get<ProcessB2CAccountTopUpUseCase>(),
            processMpesaRatibaUseCase = get<ProcessMpesaRatibaUseCase>()
        )
    }
}

