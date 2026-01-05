package com.chacha.darajacmp.di

import com.chacha.darajacmp.data.remote.DarajaRemoteDataSource
import com.chacha.darajacmp.data.remote.DarajaRemoteDataSourceImpl
import com.chacha.darajacmp.data.repository.DarajaRepositoryImpl
import com.chacha.darajacmp.domain.repository.DarajaRepository
import com.chacha.darajacmp.data.remote.AuthService
import com.chacha.darajacmp.data.remote.DarajaApiCallService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    singleOf(::AuthService)
    
    single<DarajaRemoteDataSource> { 
        DarajaRemoteDataSourceImpl(
            authService = get(),
            darajaApiCallService = get<DarajaApiCallService>()
        )
    } bind DarajaRemoteDataSource::class
    
    single<DarajaRepository> { 
        DarajaRepositoryImpl(
            remoteDataSource = get()
        )
    } bind DarajaRepository::class
}

