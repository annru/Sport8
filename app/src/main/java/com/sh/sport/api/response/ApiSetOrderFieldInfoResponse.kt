package com.sh.sport.api.response

/**
 * 提交订单返回
 */
data class ApiSetOrderFieldInfoResponse(
    val orderuid: String,//"T2207281547588078816445",
    val realExpense: Double, // "80.00",
    val expireTime: Long, // 1659524400,
    val orderid: Long,// 323667,
    val payStatus: String // "0"
)
