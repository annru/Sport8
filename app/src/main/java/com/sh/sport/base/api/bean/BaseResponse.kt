package com.sh.sport.base.api.bean

data class BaseResponse<T>(
    val returnCode: String,
    val returnMsg: String,
    val returnData: T,
    val sub_msg: String?,
    val success: Boolean
) {
    fun isSuccessful(): Boolean {
        return success
    }
}
