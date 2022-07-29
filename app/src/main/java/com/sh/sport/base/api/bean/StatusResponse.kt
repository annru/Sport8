package com.sh.sport.base.api.bean

sealed class StatusResponse<T> {
    /**
     * 业务成功
     */
    class Success<T>(response: BaseResponse<T>?) : StatusResponse<T>()

    /**
     * 业务失败
     */
    class Fail<T>(response: BaseResponse<T>?) : StatusResponse<T>()

    /**
     * 网络失败
     */
    class Failure<T>(response: BaseResponse<T>?) : StatusResponse<T>()

}

