package com.kekshi.baseproject.widget.zdyview

import android.animation.TypeEvaluator
import com.elvishew.xlog.XLog

class CharEvaluator : TypeEvaluator<Char> {
    //65-90
    override fun evaluate(fraction: Float, startValue: Char?, endValue: Char?): Char {
        XLog.e("fraction:$fraction")
        val startInt = startValue?.toInt() ?: 0
        val endInt = endValue?.toInt() ?: 0
        val curInt = (startInt + fraction * (endInt - startInt))
        XLog.e("startInt:$startInt")
        XLog.e("endInt:$endInt")
        XLog.e("curInt:$curInt")
        XLog.e("curInt.toChar():${curInt.toChar()}")
        return curInt.toChar()
    }
}