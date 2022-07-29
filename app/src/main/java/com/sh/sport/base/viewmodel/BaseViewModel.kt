package com.sport.base.viewmodel

import androidx.lifecycle.ViewModel
import com.sh.sport.base.event.LoadingEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel : ViewModel() {
    private val _progressEvent = MutableStateFlow<LoadingEvent>(LoadingEvent.LoadingEnd)
    val progressEvent = _progressEvent.asStateFlow()

    /**
     * 开启进度弹窗
     */
    suspend fun showProgressDialog() {
        _progressEvent.emit(LoadingEvent.LoadingStart)
    }

    /**
     * 关闭进度弹窗
     */
    suspend fun dismissProgressDialog() {
        _progressEvent.emit(LoadingEvent.LoadingEnd)
    }
}