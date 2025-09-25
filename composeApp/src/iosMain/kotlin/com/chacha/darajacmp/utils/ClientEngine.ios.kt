package com.chacha.darajacmp.utils

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual fun getHttpClientEngine(): HttpClientEngine = Darwin.create()
