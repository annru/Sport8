package com.sh.sport.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sh.sport.api.repository.Sport8Repository
import com.sh.sport.api.request.SubmitOrderDTO
import com.sh.sport.api.response.ApiSetOrderFieldInfoResponse
import com.sport.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class SportViewModel : BaseViewModel() {
    private val apiRepository = Sport8Repository.instance


    //提交订单
    val submitOrderLiveData = MutableLiveData<ApiSetOrderFieldInfoResponse>()


    /**
     * 提交订单
     */
    fun submitOrder(params: SubmitOrderDTO) {
        viewModelScope.launch {
            val result = apiRepository.apiSetOrderField(params)
            result?.let {
                submitOrderLiveData.value = it
            }
        }
    }
}