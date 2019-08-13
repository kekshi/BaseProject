package com.kekshi.baselib

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat


fun String.toBigDcm(): BigDecimal = let{if(it.contains(",")){BigDecimal(toNumString())}else{BigDecimal(it)}}

fun Int.toBigDcm(): BigDecimal = BigDecimal(this.toString())

fun Double.toBigDcm(): BigDecimal = BigDecimal(this.toString())

fun String.toNumString(): String{
    return this.replace(",","")
}

fun BigDecimal.toText(): String {
    var strValue = stripTrailingZeros().toString()
    if (strValue.contains("E")) {
        strValue = stripTrailingZeros().toDouble().toString()
    }
    return strValue
}

fun BigDecimal.toText(precision: Int): String { // 保留几位小数
    var format = "0.0"
    if (precision > 1) {
        for (i in 2..precision) {
            format += "0"
        }
    }
    return DecimalFormat(format).format(this)

}

fun BigDecimal.formatPrice(halfUp: Boolean, precision: Int): String {
    val formater = DecimalFormat()
    formater.maximumFractionDigits = precision
    formater.minimumFractionDigits = precision
    formater.groupingSize = 3
    formater.roundingMode = if (halfUp) RoundingMode.HALF_UP else RoundingMode.FLOOR
    return formater.format(this)
}

fun BigDecimal.formatPrice(halfUp: Boolean): String {
    val formater = DecimalFormat()
    formater.groupingSize = 3
    formater.roundingMode = if (halfUp) RoundingMode.HALF_UP else RoundingMode.FLOOR
    return formater.format(this)
}


