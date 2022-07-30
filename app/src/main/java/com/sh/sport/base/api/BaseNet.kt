package com.sh.sport.base.api

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.orhanobut.logger.Logger
import com.sh.sport.base.AppContext
import com.sh.sport.base.api.interceptor.HostInterceptor
//import com.sh.sport.base.api.interceptor.HostInterceptor
import com.sh.sport.base.api.interceptor.HttpLogInterceptor
import com.sh.sport.base.event.LoggerEvent
import com.sh.sport.base.event.LoggerEventInfo
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

open class BaseNet {
    open val apiUrl = AppContext.url
    private val readTimeOut = 10L
    private val connectTimeout = 5L
    private val writeTimeout = 10L
    private val pingInterval = 1L

    private var okHttpClient: OkHttpClient? = null

    val retrofit: Retrofit by lazy {
        retrofitBuilder().build()
    }

//    val webSocket by lazy { WebSocketWorkerImpl(okHttpClient(), 5, TimeUnit.SECONDS) }

    open fun retrofitBuilder(baseUrl: String = apiUrl): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            GsonConverterFactory.create(
                getGsonBuilder().create()
            )
        )
        .client(okHttpClient())

    open fun okHttpClient(): OkHttpClient {
        if (okHttpClient == null) {
            okHttpClient = OkHttpNotSSL.getNotSSLUnsafeOkHttpClient()
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
//                .pingInterval(pingInterval, TimeUnit.SECONDS)
                .addInterceptor(HostInterceptor())
                .addInterceptor(HttpLogInterceptor(object : HttpLogInterceptor.Logger {
                    override fun log(title: String?, message: String) {
                        LoggerEvent.launchSend(LoggerEventInfo.Tag.APi, title, message)
                        Logger.log(Logger.INFO, "api", message, null)
                    }
                }).apply {
                    level = HttpLogInterceptor.Level.BODY
                }).build()
        }
        return okHttpClient!!
    }

    private fun getGsonBuilder(): GsonBuilder {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(
            Int::class.java,
            JsonDeserializer { json: JsonElement, _: Type?, _: JsonDeserializationContext? -> json.asInt } as JsonDeserializer<Int>)
        return builder
    }

}