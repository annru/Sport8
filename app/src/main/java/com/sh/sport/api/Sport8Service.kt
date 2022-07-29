package com.sh.sport.api

import com.sh.sport.api.request.SubmitOrderDTO
import com.sh.sport.api.response.ApiSetOrderFieldInfoResponse
import com.sh.sport.base.api.bean.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface Sport8Service {

    /**
     * 提交订单
     * http://yd8.sports8.com.cn/api/ydb/stadium/apiSetOrderField
     */
    @POST("/api/ydb/stadium/apiSetOrderField")
    suspend fun apiSetOrderField(@Body params: SubmitOrderDTO): BaseResponse<ApiSetOrderFieldInfoResponse>


}