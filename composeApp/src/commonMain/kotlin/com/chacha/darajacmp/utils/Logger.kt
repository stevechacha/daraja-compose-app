package com.chacha.darajacmp.utils

import co.touchlab.kermit.Logger

interface DarajaLogger {
    fun debug(message: String)
    fun info(message: String)
    fun warn(message: String)
    fun error(message: String, throwable: Throwable? = null)
}

object KermitLogger : DarajaLogger {
    override fun debug(message: String) {
        Logger.d(message)
    }

    override fun info(message: String) {
        Logger.i(message)
    }

    override fun warn(message: String) {
        Logger.w(message)
    }

    override fun error(message: String, throwable: Throwable?) {
        Logger.e(message, throwable)
    }
}

object AppLogger {
    private var logger: DarajaLogger = KermitLogger
    
    fun initialize(logger: DarajaLogger) {
        AppLogger.logger = logger
    }
    
    fun debug(message: String) = logger.debug(message)
    fun info(message: String) = logger.info(message)
    fun warn(message: String) = logger.warn(message)
    fun error(message: String, throwable: Throwable? = null) = logger.error(message, throwable)
}
