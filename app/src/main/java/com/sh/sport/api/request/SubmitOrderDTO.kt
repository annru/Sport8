package com.sh.sport.api.request

data class SubmitOrderDTO(
    val biz: String,
    val date: String,
    val method: String,
    val nonce: String,
    val userid: String,
    val orderList: List<OrderItem>,//[{"startTime":18,"endTime":"19","fieldid":"513"}]
    val orderid: String = "",
    val sign: String,
    val stadiumid: String
)


data class OrderItem(
    val startTime: String,
    val endTime: String,
    val fieldid: String = "513"
)
