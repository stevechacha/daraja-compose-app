package com.chacha.darajacmp.utils

import com.chacha.darajacmp.di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
//        modules(appModule(),platformModule)
    }
}