package com.chacha.darajacmp.utils

import io.ktor.client.engine.HttpClientEngine


// Platform-specific HttpClient engines
expect fun getHttpClientEngine(): HttpClientEngine