package com.sh.sport.base.event

/**
 * 开启或者关闭loading
 */
sealed interface LoadingEvent {
    object LoadingStart : LoadingEvent
    object LoadingEnd : LoadingEvent
}