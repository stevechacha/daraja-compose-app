package com.chacha.darajacmp.di

import io.ktor.client.engine.*
import io.ktor.client.engine.android.Android

actual fun getHttpClientEngine(): HttpClientEngine = Android.create()

