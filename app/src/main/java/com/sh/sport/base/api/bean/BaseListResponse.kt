package com.sh.sport.base.api.bean

data class BaseListResponse<T>(
    var pageNum: Int = 0,
    val pageSize: Int = 0,
    val total: Int = 0,//总条数
    val pages: Int = 0,//总页数
    val list: List<T>? = null
)