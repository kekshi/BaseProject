package com.kekshi.baseproject.widget.zdyview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin


class RedPointView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mStartPoint: PointF = PointF(100f, 100f)
    private var mCurPoint: PointF = PointF()
    private var mRadius = 20f
    private var mPaint: Paint = Paint()
    private var mPath: Path = Path()
    private var mTouch: Boolean = false

    init {
        mPaint.color = Color.RED
        mPaint.style = Paint.Style.FILL
    }

    /**
     * 绘制子类
     */
    override fun dispatchDraw(canvas: Canvas?) {
        if (canvas == null) return

        canvas.saveLayer(RectF(0f, 0f, width.toFloat(), height.toFloat()), mPaint)
        canvas.drawCircle(mStartPoint.x, mStartPoint.y, mRadius, mPaint)//绘制起点的圆
        if (mTouch) {
            calculatePath()
            canvas.drawCircle(mCurPoint.x, mCurPoint.y, mRadius, mPaint)
            canvas.drawPath(mPath, mPaint)
        }
        canvas.restore()

        super.dispatchDraw(canvas)
    }

    private fun calculatePath() {
        val x = mCurPoint.x
        val y = mCurPoint.y
        val startX = mStartPoint.x
        val startY = mStartPoint.y
        // 根据角度算出四边形的四个点
        val dx = x - startX
        val dy = y - startY
        val atan = atan(dy / dx.toDouble())
        val offsetX = mRadius * sin(atan).toFloat()
        val offsetY = mRadius * cos(atan).toFloat()

        // 根据角度算出四边形的四个点
        val x1 = startX + offsetX
        val y1 = startY + offsetY

        val x2 = x + offsetX
        val y2 = y - offsetY

        val x3 = x - offsetX
        val y3 = y + offsetY

        val x4 = startX - offsetX
        val y4 = startY + offsetY


        val anchorX = (startX + x) / 2
        val anchorY = (startY + y) / 2

        mPath.reset()
        mPath.moveTo(x1, y1)
        mPath.quadTo(anchorX, anchorY, x2, y2)
        mPath.lineTo(x3, y3)
        mPath.quadTo(anchorX, anchorY, x4, y4)
        mPath.lineTo(x1, y1)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return super.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mTouch = true
            }
            MotionEvent.ACTION_UP -> {
                mTouch = false
            }
        }
        mCurPoint.set(event.x, event.y)
        invalidate()
        return true
    }
}