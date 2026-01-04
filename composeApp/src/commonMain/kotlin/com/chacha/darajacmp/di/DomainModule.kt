package com.chacha.darajacmp.di

import com.chacha.darajacmp.domain.repository.DarajaRepository
import com.chacha.darajacmp.domain.service.DarajaService
import com.chacha.darajacmp.domain.service.DarajaServiceImpl
import com.chacha.darajacmp.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    single<DarajaService> { DarajaServiceImpl() }
    
    // UseCases
    factory { 
        InitiateSTKPushUseCase(
            repository = get<DarajaRepository>(),
            darajaService = get<DarajaService>()
        )
    }
    
    factory {
        QuerySTKStatusUseCase(
            repository = get<DarajaRepository>(),
            darajaService = get<DarajaService>()
        )
    }
    
    factory { RegisterC2BURLUseCase(repository = get<DarajaRepository>()) }
    factory { SimulateC2BUseCase(repository = get<DarajaRepository>()) }
    factory { InitiateB2CUseCase(repository = get<DarajaRepository>()) }
    factory { GenerateDynamicQRUseCase(repository = get<DarajaRepository>()) }
    factory { CheckAccountBalanceUseCase(repository = get<DarajaRepository>()) }
    factory { QueryTransactionStatusUseCase(repository = get<DarajaRepository>()) }
    factory { ReverseTransactionUseCase(repository = get<DarajaRepository>()) }
    factory { RemitTaxUseCase(repository = get<DarajaRepository>()) }
    factory { ProcessBusinessPayBillUseCase(repository = get<DarajaRepository>()) }
    factory { ProcessBusinessBuyGoodsUseCase(repository = get<DarajaRepository>()) }
    factory { ProcessBillManagerUseCase(repository = get<DarajaRepository>()) }
    factory { ProcessB2BExpressCheckOutUseCase(repository = get<DarajaRepository>()) }
    factory { ProcessB2CAccountTopUpUseCase(repository = get<DarajaRepository>()) }
    factory { ProcessMpesaRatibaUseCase(repository = get<DarajaRepository>()) }
}

