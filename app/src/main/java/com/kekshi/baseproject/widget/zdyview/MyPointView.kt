package com.kekshi.baseproject.widget.zdyview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.BounceInterpolator
import com.elvishew.xlog.XLog
import com.kekshi.baseproject.bean.Point


class MyPointView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var mCurPoint: Point? = null

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mCurPoint != null) {
            val paint = Paint()
            paint.isAntiAlias = true
            paint.color = Color.RED
            paint.style = Paint.Style.FILL

            canvas?.drawCircle(300f, 300f, mCurPoint!!.radius, paint)
        }
    }

    fun doPointAnim() {
        val animator = ValueAnimator.ofObject(PointEvaluator(), Point(20f), Point(200f))
        XLog.e("我被调用了")
        animator.addUpdateListener {
            mCurPoint = it.animatedValue as Point
            XLog.e("mCurPoint radius:${mCurPoint?.radius}")
            invalidate()
        }
        animator.duration = 1000
        animator.interpolator = BounceInterpolator()
        animator.start()
    }
}