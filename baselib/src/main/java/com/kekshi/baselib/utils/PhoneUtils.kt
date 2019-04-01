package com.kekshi.baselib.utils


import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager

object PhoneUtils {
    // imei
    @Suppress("DEPRECATION")
    @SuppressLint("MissingPermission", "HardwareIds")
    fun getImei(context: Context): String {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.deviceId
    }

    // 手机制造商
    fun getProduct(): String {
        return Build.PRODUCT
    }

    // 系统版本
    fun getAndroidVersion(): String {
        return Build.VERSION.RELEASE
    }

    // 型号
    fun getModel(): String {
        return Build.MODEL
    }

    // 手机厂商
    fun getDeviceBrand(): String {
        return Build.BRAND
    }

    /**
     * 获取本地软件版本号
     */
    fun getLocalVersion(ctx: Context): Int {
        var localVersion = 0
        try {
            val packageInfo = ctx.applicationContext
                .packageManager
                .getPackageInfo(ctx.packageName, 0)
            localVersion = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                packageInfo.longVersionCode.toInt()
            }else{
                packageInfo.versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return localVersion
    }

    /**
     * 获取本地软件版本号名称
     */
    fun getLocalVersionName(ctx: Context): String {
        var localVersion = ""
        try {
            val packageInfo = ctx.applicationContext
                .packageManager
                .getPackageInfo(ctx.packageName, 0)
            localVersion = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return localVersion
    }
}