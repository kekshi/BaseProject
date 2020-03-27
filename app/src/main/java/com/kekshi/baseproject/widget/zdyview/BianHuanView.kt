package com.kekshi.baseproject.widget.zdyview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class BianHuanView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        translate(canvas)

//        rotate(canvas)

        scale(canvas)
    }

    private fun scale(canvas: Canvas?) {
        val paint_green = generatePaint(Color.GREEN, Paint.Style.STROKE, 3f)
        val paint_red = generatePaint(Color.RED, Paint.Style.STROKE, 3f)

        val rect1 = Rect(10, 10, 200, 100)
        canvas?.drawRect(rect1, paint_green)
        canvas?.scale(0.5f, 1f)
        canvas?.drawRect(rect1, paint_red)
    }

    private fun rotate(canvas: Canvas?) {
        val paint_green = generatePaint(Color.GREEN, Paint.Style.FILL, 3f)
        val paint_red = generatePaint(Color.RED, Paint.Style.STROKE, 3f)

        val rect1 = Rect(300, 10, 500, 100)
        canvas?.drawRect(rect1, paint_green)
        canvas?.rotate(30f, 100f, 100f)
        canvas?.drawRect(rect1, paint_red)
    }

    private fun translate(canvas: Canvas?) {
        //translate  平移,即改变坐标系原点位置
        //构造两个画笔，一个红色，一个绿色
        val paint_green = generatePaint(Color.GREEN, Paint.Style.STROKE, 3f)
        val paint_red = generatePaint(Color.RED, Paint.Style.STROKE, 3f)

        val rect1 = Rect(0, 0, 400, 220)
        canvas?.drawRect(rect1, paint_green)
        canvas?.translate(100f, 100f)
        canvas?.drawRect(rect1, paint_red)
    }

    private fun generatePaint(color: Int, style: Paint.Style, width: Float): Paint {
        val paint = Paint()
        paint.color = color
        paint.style = style
        paint.strokeWidth = width
        return paint
    }
}