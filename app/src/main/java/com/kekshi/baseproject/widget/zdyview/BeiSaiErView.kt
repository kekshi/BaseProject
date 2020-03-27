package com.kekshi.baseproject.widget.zdyview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator


class BeiSaiErView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val path: Path = Path()
    private val paint = Paint()
    private var mPreX: Float = 0f
    private var mPreY: Float = 0f
    private val mItemWaveLength = 1000
    private var dx: Int = 0

    init {
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = Color.GREEN
    }

    //手写板需要监听事件
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        event?.action?.let {
//            when (it) {
//                MotionEvent.ACTION_DOWN -> {
//                    path.moveTo(event.x, event.y)
//                    mPreX = event.x
//                    mPreY = event.y
//                    return true
//                }
//                MotionEvent.ACTION_MOVE -> {
//                    //线段转折处不够平滑
////                    path.lineTo(event.x, event.y)
//                    /**
//                     * 我们先找到结束点，我们说了结束点是这个线段的中间位置，所以很容易求出它的坐标endX,endY；控制点是上一个手指位置即mPreX,mPreY;
//                     * 那有些同学可能会问了，那起始点是哪啊。在开篇讲quadTo()函数时，就已经说过，第一个起始点是Path.moveTo(x,y)定义的，
//                     * 其它部分，一个quadTo的终点，是下一个quadTo的起始点。所以这里的起始点，就是上一个线段的中间点。
//                     * 所以，这样就与钢笔工具绘制过程完全对上了：把各个线段的中间点做为起始点和终点，把终点前一个手指位置做为控制点。
//                     */
//                    val endX = (mPreX + event.x) / 2
//                    val endY = (mPreY + event.y) / 2
//                    path.quadTo(mPreX, mPreY, endX, endY)
//                    mPreX = event.x
//                    mPreY = event.y
//                    postInvalidate()
//                }
//                MotionEvent.ACTION_UP -> {
//                }
//            }
//        }
//        return super.onTouchEvent(event)
//    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        val path = Path()
//        path.moveTo(100f, 300f)
//        //第一个参数控制点的位置，第二个参数终点的位置
////        path.quadTo(500f, 300f, 500f, 500f)
//        path.rQuadTo(100f, -100f, 200f, 0f)
//        path.rQuadTo(100f, 100f, 200f, 0f)
//
//        canvas?.drawPath(path, paint)

        path.reset()
        val originY = 800f
        val halfWaveLen = mItemWaveLength / 2
        path.moveTo((-mItemWaveLength + dx).toFloat(), originY + dx)
        for (item in -mItemWaveLength..width + mItemWaveLength step mItemWaveLength) {
            path.rQuadTo(halfWaveLen / 2.toFloat(), -100f, halfWaveLen.toFloat(), 0f)
            path.rQuadTo(halfWaveLen / 2.toFloat(), 100f, halfWaveLen.toFloat(), 0f)
        }
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(0f, height.toFloat())
        path.close()

        canvas?.drawPath(path, paint)
    }

    fun startAnimator() {
        val animator = ValueAnimator.ofInt(0, mItemWaveLength)
        animator.duration = 2000
        animator.repeatCount = ValueAnimator.INFINITE//无限重复
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            dx = animation.animatedValue as Int
            postInvalidate()
        }
        animator.start()
    }

    fun reset() {
        path.reset()
        invalidate()
    }
}