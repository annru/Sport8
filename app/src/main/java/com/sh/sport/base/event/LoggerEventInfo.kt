package com.sh.sport.base.event

data class LoggerEventInfo(
    val type: Int,
    val title: String?,
    val content: String?,
    val time: Long
) {
    enum class Tag(val type: Int, val value: String) {
        APi(1, "api"),
        CASE(2, "case"),
        CRASH(3, "crash"),
        INTERACTIVE(4, "interactive"),
        Push(5, "push"),
        ERROR(6, "error"),
    }
}