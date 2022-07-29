package com.sh.sport

import com.sh.sport.base.BaseApplication

class SportApplication : BaseApplication() {

    companion object {
        lateinit var instance: SportApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    override fun handlerCrash(logPath: String?, emergency: String?) {

    }
}