package com.chacha.darajacmp.di

import com.chacha.darajacmp.data.network.DarajaApiCallService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val darajaModule = module {
    
    // Daraja API Service with credentials
    single<DarajaApiCallService> {
        DarajaApiCallService(
            consumerKey = "xkS5JzqHgNItCXl29G9PWqdQqAH5Tb2cVxU1pi83GFHHtGSZ",
            consumerSecret = "7Xo6rVHVdQxXfnU8sSR77Af0ibU2RaPJGXAhouaGHA3dnuq1e1seZKSt5b25bOpg"
        )
    }
}


