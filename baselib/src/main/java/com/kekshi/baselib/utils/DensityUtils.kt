package com.kekshi.baselib.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Point



object DensityUtils {
    fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun sp2px(spValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.scaledDensity
        return (spValue * scale + 0.5f).toInt()
    }

    fun getScreenWidth(): Int {
        val dm = Resources.getSystem().displayMetrics
        return dm.widthPixels
    }

    fun getStatusBarHeight(): Int {
        var result = 0
//        if (CacheUtils.contains("status_bar_height")) {
//            return CacheUtils.getInt("status_bar_height")
//        }
//
//        val res = Resources.getSystem()
//        val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
//        if (resourceId > 0) {
//            result = res.getDimensionPixelSize(resourceId)
//        }
//
//        CacheUtils.putInt("status_bar_height", result)
        return result
    }

    fun getNavigationBarHeight(): Int {
        var result = 0
//        if (CacheUtils.contains("navigation_bar_height")) {
//            return CacheUtils.getInt("navigation_bar_height")
//        }
        if (hasNavBar()) {
            val res = Resources.getSystem()
            val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId)
            }
        }
//        CacheUtils.putInt("navigation_bar_height", result)
        return result
    }

    fun hasNavBar(): Boolean {
        val res = Resources.getSystem()
        val resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android")
        if (resourceId != 0) {
            var hasNav = res.getBoolean(resourceId)
            // check override flag
            val sNavBarOverride = getNavBarOverride()
            if ("1" == sNavBarOverride) {
                hasNav = false
            } else if ("0" == sNavBarOverride) {
                hasNav = true
            }
            return hasNav
        } else { // fallback
            return false
        }
    }

    fun isNavBarShow(context: Context): Boolean {
        val display = (context as Activity).windowManager.getDefaultDisplay()
        val size = Point()
        val realSize = Point()
        display.getSize(size)
        display.getRealSize(realSize)
        return realSize.y != size.y
    }

    @SuppressLint("PrivateApi")
    private fun getNavBarOverride(): String? {
        var sNavBarOverride: String? = null
        try {
            val c = Class.forName("android.os.SystemProperties")
            val m = c.getDeclaredMethod("get", String::class.java)
            m.isAccessible = true
            sNavBarOverride = m.invoke(null, "qemu.hw.mainkeys") as String
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return sNavBarOverride
    }

}
