package com.sh.sport.base.api.response

import com.sh.sport.R
import com.sh.sport.base.api.bean.BaseResponse
import com.sh.sport.base.api.bean.StatusResponse
import com.sh.sport.base.utils.ToastUtil
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 处理网络请求，返回状态和结果
 */
suspend fun <T> Call<BaseResponse<T>>.awaitStatusResult(showToast: Boolean = true):
        StatusResponse<T> {
    return suspendCoroutine {
        enqueue(object : Callback<BaseResponse<T>> {
            override fun onResponse(
                call: Call<BaseResponse<T>>,
                response: Response<BaseResponse<T>>
            ) {
                val body = response.body()
                val statusResponse = if (isResponseSuccess(
                        response
                    )
                ) {
                    StatusResponse.Success(body)
                } else {
                    if (showToast) {
                        ToastUtil.show(response.body()?.returnMsg)
                    }
                    StatusResponse.Fail(body)
                }
                it.resume(statusResponse)
            }

            override fun onFailure(call: Call<BaseResponse<T>>, t: Throwable) {
                if (showToast) {
                    ToastUtil.show(R.string.net_error)
                }
                it.resume(StatusResponse.Failure(null))
            }
        })
    }
}

/**
 * 处理网络请求，仅返回结果
 */
suspend fun <T> Call<BaseResponse<T>>.awaitResult(showToast: Boolean = true): T? {
    return suspendCoroutine {
        enqueue(object : Callback<BaseResponse<T>> {
            override fun onResponse(
                call: Call<BaseResponse<T>>,
                response: Response<BaseResponse<T>>
            ) {
                if (isResponseSuccess(response)) {
                    it.resume(response.body()?.returnData)
                } else {
                    if (showToast) {
                        ToastUtil.show(response.body()?.returnMsg)
                    }
                    it.resume(null)
                }
            }

            override fun onFailure(call: Call<BaseResponse<T>>, t: Throwable) {
                if (showToast) {
                    ToastUtil.show(R.string.net_error)
                }
                it.resume(null)
            }
        })
    }
}


/**
 * 处理网络请求，仅返回成功或者失败。
 */
suspend fun <T> Call<BaseResponse<T>>.awaitBoolean(showToast: Boolean = true): Boolean {
    return suspendCoroutine {
        enqueue(object : Callback<BaseResponse<T>> {
            override fun onResponse(
                call: Call<BaseResponse<T>>,
                response: Response<BaseResponse<T>>
            ) {
                if (isResponseSuccess(response)) {
                    it.resume(true)
                } else {
                    if (showToast) {
                        ToastUtil.show(response.body()?.returnMsg)
                    }
                    it.resume(false)
                }
            }

            override fun onFailure(call: Call<BaseResponse<T>>, t: Throwable) {
                if (showToast) {
                    ToastUtil.show(R.string.net_error)
                }
                it.resume(false)
            }
        })
    }
}

/**
 * 判断请求是否在业务上成功
 */
private fun <T> isResponseSuccess(response: Response<BaseResponse<T>>): Boolean {
    val apiResponse = response.body()
    return !(!response.isSuccessful || apiResponse == null || !apiResponse.isSuccessful())
}

/**
 * 处理网络请求，仅返回成功或者失败。
 */
inline fun awaitBoolean(showToast: Boolean = true, block: () -> BaseResponse<*>): Boolean {
    return try {
        val result = block()
        if (result.isSuccessful()) {
            true
        } else {
            if (showToast)
                ToastUtil.show(result.returnMsg)
            false
        }
    } catch (e: Exception) {
        if (showToast)
            ToastUtil.show(R.string.net_error)
        false
    }
}

/**
 * 处理网络请求，返回结果。
 */
inline fun <T> awaitResult(showToast: Boolean = true, block: () -> BaseResponse<T>): T? {
    return try {
        val result = block()
        if (result.isSuccessful()) {
            result.returnData
        } else {
            if (showToast)
                ToastUtil.show(result.returnMsg)
            null
        }
    } catch (e: Exception) {
        if (showToast)
            ToastUtil.show(R.string.net_error)
        null
    }
}

inline fun awaitFile(filePath: String, block: () -> ResponseBody) {
    val result = block()

    val file = File(filePath)
    if (!file.exists()) {
        file.createNewFile()
    }

    file.outputStream().use { fos ->
        BufferedOutputStream(fos).use { bos ->
            result.byteStream().use { inputStream ->
                BufferedInputStream(inputStream).use { bis ->
                    bos.write(bis.readBytes())
                }
            }
        }
    }
}