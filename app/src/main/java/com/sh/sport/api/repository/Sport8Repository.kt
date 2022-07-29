package com.sh.sport.api.repository

import com.sh.sport.api.Sport8Service
import com.sh.sport.api.request.SubmitOrderDTO
import com.sh.sport.base.BaseApplication
import com.sh.sport.base.api.response.awaitResult

class Sport8Repository {
    private val api by lazy { BaseApplication.retrofitCreate(Sport8Service::class.java) }

    suspend fun apiSetOrderField(params: SubmitOrderDTO) = awaitResult {
        api.apiSetOrderField(params)
    }

    companion object {
        val instance: Sport8Repository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            Sport8Repository()
        }
    }
}