package com.sh.sport.base.api.interceptor

import com.sh.sport.base.AppContext
import com.sh.sport.base.api.constant.NetConstant
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HostInterceptor : Interceptor {
//    private val deviceId = getDeviceId(appContext)
//    private val version = getVersionName(appContext) ?: ""

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val builder =
            request.newBuilder()
                .addHeader("Content-Length", request.body?.contentLength().toString())
//                .addHeader("appVer", "")
        return if (request.headers[NetConstant.KEY_REMAIN] == NetConstant.KEY_REMAIN) {
            chain.proceed(builder.build())
        } else {
            AppContext.url.toHttpUrl().let {
                val newUrl = request.url.newBuilder()
                newUrl.scheme(it.scheme)
                newUrl.host(it.host)
                newUrl.port(it.port)
                newUrl.encodedPath(request.url.encodedPath)
                builder.url(newUrl.build())
            }
            request = builder.build()
            chain.proceed(request)
        }
    }
}