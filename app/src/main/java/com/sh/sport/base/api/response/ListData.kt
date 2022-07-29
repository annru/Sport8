package com.sh.sport.base.api.response

import com.sh.sport.base.api.bean.BaseListResponse

data class ListData<T>(
    var isAdd: Boolean = false,
    var hasMore: Boolean = false,
    var data: List<T>? = null,
    var index: Int = 0
)

fun <T> generateListData(result: BaseListResponse<T>?): ListData<T>? {
    if (result == null) return null
    val data = ListData<T>()
    data.data = result.list
    data.isAdd = result.pageNum != 1
    data.hasMore = result.pageNum * result.pageSize < result.total
    data.index = result.pageNum
    return data
}

inline fun <R, T> generateListData(
    result: BaseListResponse<R>?,
    func: (List<R>?) -> List<T>?
): ListData<T>? {
    if (result == null) return null
    val data = ListData<T>()
    data.data = func(result.list)
    data.isAdd = result.pageNum != 1
    data.hasMore = result.pageNum * result.pageSize < result.total
    data.index = result.pageNum
    return data
}