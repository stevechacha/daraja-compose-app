package com.chacha.darajacmp.di

import io.ktor.client.engine.android.Android
import io.ktor.client.engine.*

actual fun getHttpClientEngine(): HttpClientEngine = CIO.create()

