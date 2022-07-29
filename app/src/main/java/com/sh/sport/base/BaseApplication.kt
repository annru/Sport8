package com.sh.sport.base

import android.app.Application
import android.content.Context
import com.orhanobut.logger.*
import com.sh.sport.base.api.BaseNet
import com.sh.sport.base.utils.ToastUtil
import com.tencent.mmkv.MMKV
import xcrash.ICrashCallback
import xcrash.XCrash
import java.io.File

abstract class BaseApplication : Application(), ICrashCallback {
    companion object {
        val net by lazy { BaseNet() }

        fun <T> retrofitCreate(service: Class<T>): T {
            return net.retrofit.create(service)
        }

        const val LOG_TAG = "sport8"
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        val params = XCrash.InitParameters()
        params
            .setLogDir(getExternalFilesDir(null)?.absolutePath + File.separator + "/error")
            .setJavaRethrow(true)
            .setNativeRethrow(true)
            .setAnrRethrow(true)
            .setJavaCallback(this)
            .setNativeCallback(this)
            .setAnrCallback(this)
        XCrash.init(this, params)
    }

    override fun onCreate() {
        super.onCreate()
        ToastUtil.initialize(this, ToastUtil.Mode.REPLACEABLE)
        MMKV.initialize(this)
        initLogger()
//        TTSUtils.instance.init(this)
        AppContext.init(this)
    }

    private fun initLogger() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodCount(0)
            .tag(LOG_TAG)
            .build()

        val diskStrategy = CsvFormatStrategy.newBuilder()
            .tag(LOG_TAG)
            .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
        Logger.addLogAdapter(DiskLogAdapter(diskStrategy))
    }

    abstract fun handlerCrash(logPath: String?, emergency: String?)

    override fun onCrash(logPath: String?, emergency: String?) {
        handlerCrash(logPath, emergency)
    }
}