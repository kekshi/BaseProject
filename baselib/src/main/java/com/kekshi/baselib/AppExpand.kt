package com.kekshi.baselib

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.TextUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * 复制内容到剪切板
 */
fun Context.copyAddress(str: String) {
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    //创建ClipData对象
    val clipData = ClipData.newPlainText("copy", str)
    //添加ClipData对象到剪切板中
    clipboardManager.primaryClip = clipData
}

fun getTimeStr(time: String): String {
    if (TextUtils.isEmpty(time)) {
        return ""
    }
    //返回的时间戳若是10位则要乘以1000，如果是13位就不用
    val d = Date(time.toLong() * 1000)
    val sf = SimpleDateFormat("yyyy年MM月dd HH:mm:ss")
    return sf.format(d)
}