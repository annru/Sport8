package com.sh.sport.base.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

object LoggerEvent {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val _logger = MutableSharedFlow<LoggerEventInfo>()
    val logger = _logger

    suspend fun send(tag: LoggerEventInfo.Tag, title: String?, msg: String?) {
        _logger.emit(LoggerEventInfo(tag.type, title, msg, System.currentTimeMillis()))
    }

    fun launchSend(tag: LoggerEventInfo.Tag, title: String?, msg: String?) {
        scope.launch {
            _logger.emit(LoggerEventInfo(tag.type, title, msg, System.currentTimeMillis()))
        }
    }

    fun cancel() {
        scope.cancel()
    }
}