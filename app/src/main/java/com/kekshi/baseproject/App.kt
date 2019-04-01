package com.kekshi.baseproject

import com.kekshi.baselib.base.BaseApp
import com.squareup.leakcanary.LeakCanary


class App: BaseApp() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }

}