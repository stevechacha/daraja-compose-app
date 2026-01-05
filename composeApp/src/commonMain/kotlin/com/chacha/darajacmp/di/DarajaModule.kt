package com.chacha.darajacmp.di

import com.chacha.darajacmp.data.remote.DarajaApiCallService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val darajaModule = module {
    
    // Daraja API Service with credentials from BuildKonfig
    single<DarajaApiCallService> {
        DarajaApiCallService(
            consumerKey = com.chacha.darajacmp.BuildKonfig.CLIENT_ID,
            consumerSecret = com.chacha.darajacmp.BuildKonfig.CLIENT_SECRET
        )
    }
}


