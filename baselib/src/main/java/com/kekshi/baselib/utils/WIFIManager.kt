package com.kekshi.baselib.utils

import android.content.Context
import android.net.wifi.WifiManager

class WIFIManager(val context: Context) {
    var wifiManager: WifiManager

    init {
        wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

    fun getWifiInfo() {
        val wifiInfo = wifiManager.connectionInfo
    }
}