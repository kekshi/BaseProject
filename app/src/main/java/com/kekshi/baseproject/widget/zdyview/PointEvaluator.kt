package com.kekshi.baseproject.widget.zdyview

import android.animation.TypeEvaluator
import com.elvishew.xlog.XLog
import com.kekshi.baseproject.bean.Point

class PointEvaluator : TypeEvaluator<Point> {
    //65-90
    override fun evaluate(fraction: Float, startValue: Point?, endValue: Point?): Point {
        val startInt = startValue?.radius ?: 0f
        val endInt = endValue?.radius ?: 0f
        val curInt = (startInt + fraction * (endInt - startInt))
        XLog.e("curInt :$curInt")
        return Point(curInt)
    }
}