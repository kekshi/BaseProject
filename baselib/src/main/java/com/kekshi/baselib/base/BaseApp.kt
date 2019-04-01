package com.kekshi.baselib.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.kekshi.baselib.BuildConfig

open class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this

        XLog.init(if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}