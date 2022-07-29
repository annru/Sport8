package com.sh.sport.base

import android.app.Application
import com.sh.sport.base.api.constant.NetConstant

lateinit var appContext: Application

object AppContext {
    var url: String
        get() {
            return NetConstant.getHttpUrl()
        }
        set(value) {
            NetConstant.setHttpUrl(value)
        }

    fun init(app: Application) {
        appContext = app
    }
}