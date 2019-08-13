package com.kekshi.baselib

import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import java.text.NumberFormat


fun String.replaceName(begin: Int = 0, end: Int): String {
    if (begin >= this.length || begin < 0) {
        return this
    }
    if (end >= this.length || end < 0) {
        return this
    }
    if (begin >= end) {
        return this
    }
    var starStr = ""
    for (i in begin until end) {
        starStr = "$starStr*"
    }
    return this.substring(0, begin) + starStr + this.substring(end, this.length)
}

fun String.formatPriceTextFormat(precision: Int): String {
    val price = this.toBigDcm().toDouble()
    val nf = NumberFormat.getInstance()
    nf.maximumFractionDigits = precision
    val newPrice = nf.format(price)
    if (newPrice.contains(".")) {
        return newPrice
    } else {
        return newPrice.toBigDcm().toText(2)
    }
}

fun String.formatPriceTextSize(yuanSize: Int, centSize: Int): SpannableString {
    val pointIndex = indexOf(".")
    if(pointIndex == -1){
        return SpannableString(this)
    }

    return SpannableString(this)
            .apply {
                setSpan(AbsoluteSizeSpan(yuanSize,true), 0, pointIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(AbsoluteSizeSpan(centSize,true), pointIndex+1, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
}

fun String.formatNumberTextSize(yuanSize: Int, centSize: Int, sign: String): SpannableString {
    val pointIndex = indexOf(sign)
    if(pointIndex == -1){
        return SpannableString(this)
    }

    return SpannableString(this)
            .apply {
                setSpan(AbsoluteSizeSpan(yuanSize,true), 0, pointIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(AbsoluteSizeSpan(centSize,true), pointIndex+1, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
}