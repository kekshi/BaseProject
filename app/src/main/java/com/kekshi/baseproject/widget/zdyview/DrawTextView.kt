package com.kekshi.baseproject.widget.zdyview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.elvishew.xlog.XLog


class DrawTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        drawText(canvas)

//        drawFontMetrics(canvas)

//        topJiSuanBaseLineY(canvas)

        centerJiSuanBaseLineY(canvas)
    }

    /**
     * 知道中线位置，求基线 Y 的位置
     *
     * 总共有四条线：top线，bottom线，baseline和center线；
     *   图中center线正是在top线和bottom线的正中间。
     *   为了方便推导公式，我另外标了三个距离A（中间线-fontMetrics.top）,B（中间线-基线Y）,C（中间线-fontMetrics.bottom）;
     *   很显然，距离A和距离C是相等的，都等于文字所在矩形高度以的一半，即：
     *   A = C = (bottom - top)/2;
     *   又因为bottom = baseline + FontMetrics.bottom;
     *   top = baseline+FontMetrics.top;
     *   所以，将它们两个代入上面的公式，就可得到：
     *   A = C = (FontMetrics.bottom - FontMetrics.top)/2;
     *   而距离B,则表示Center线到baseline的距离。
     *   很显然距离B = C - (bottom - baseline);
     *   又因为
     *   FontMetrics.bottom = bottom-baseline;
     *   C = A;
     *   所以，B = A - FontMetrics.bottom;
     *   所以baseline = center + B = center + A - FontMetrics.bottom = center + (FontMetrics.bottom - FontMetrics.top)/2 - FontMetrics.bottom;
     */
    private fun centerJiSuanBaseLineY(canvas: Canvas?) {
        val text = "harvic\'s blog"
        val baseLineX = 0f
        val center = 200f
        //画基线
        val paint = Paint()
        paint.strokeWidth = 5f
        paint.textSize = 120f; //以px为单位
        paint.textAlign = Paint.Align.LEFT //对齐方式为原点的左边

        //画 center 线
        paint.setColor(Color.YELLOW);
        canvas?.drawLine(baseLineX, center, 3000f, center, paint)
        val fontMetrics = paint.fontMetrics
        val baseLineY = center + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;

        //画基线
        paint.setColor(Color.RED);
        canvas?.drawLine(baseLineX, baseLineY, 3000f, baseLineY, paint);

        //写文字
        paint.setColor(Color.GREEN);
        canvas?.drawText(text, baseLineX, baseLineY, paint);
    }

    /**
     * 知道矩形左上角顶点(left,top)，求基线 Y 的位置
     */
    private fun topJiSuanBaseLineY(canvas: Canvas?) {
        val text = "harvic\'s blog"
        val baseLineX = 0f
        val top = 200f
        //画基线
        val paint = Paint()
        paint.strokeWidth = 5f
        paint.textSize = 120f; //以px为单位
        paint.textAlign = Paint.Align.LEFT //对齐方式为原点的左边

        //画top线
        paint.setColor(Color.YELLOW);
        canvas?.drawLine(baseLineX, top, 3000f, top, paint)

        //计算出baseLine位置 fontMetrics中的值以基线为基准，基线之上的为负值，基线之下的为正值。
        val fontMetrics = paint.fontMetrics
        val baseLineY = top - fontMetrics.top

        //画基线
        paint.setColor(Color.RED);
        canvas?.drawLine(baseLineX, baseLineY, 3000f, baseLineY, paint);

        //写文字
        paint.setColor(Color.GREEN);
        canvas?.drawText(text, baseLineX, baseLineY, paint);
    }

    private fun drawFontMetrics(canvas: Canvas?) {
        val baseLineX = 0f
        val baseLineY = 200f
        //画基线
        val paint = Paint()
        paint.color = Color.RED
        paint.strokeWidth = 3f
        paint.textSize = 120f; //以px为单位
        paint.textAlign = Paint.Align.LEFT //对齐方式为原点的左边

        val fontMetrics = paint.fontMetrics
        XLog.e("ddddd-fontMetrics is:${fontMetrics.toString()}")
        val ascent = baseLineY + fontMetrics.ascent
        val bottom = baseLineY + fontMetrics.bottom
        val descent = baseLineY + fontMetrics.descent
        val top = baseLineY + fontMetrics.top

        canvas?.drawText("harvic\'s blog", baseLineX, baseLineY, paint);
        //画基线
        paint.color = Color.RED
        canvas?.drawLine(baseLineX, baseLineY, 3000f, baseLineY, paint)

        //画top
        paint.setColor(Color.BLUE);
        canvas?.drawLine(baseLineX, top, 3000f, top, paint);

        //画ascent
        paint.setColor(Color.GREEN);
        canvas?.drawLine(baseLineX, ascent, 3000f, ascent, paint);

        //画descent
        paint.setColor(Color.YELLOW);
        canvas?.drawLine(baseLineX, descent, 3000f, descent, paint);

        //画bottom
        paint.setColor(Color.RED);
        canvas?.drawLine(baseLineX, bottom, 3000f, bottom, paint);

    }

    private fun drawText(canvas: Canvas?) {
        val baseLineX = 0f
        val baseLineY = 200f

        //画基线
        val paint = Paint()
        paint.color = Color.RED
        paint.strokeWidth = 3f
        canvas?.drawLine(baseLineX, baseLineY, 3000f, baseLineY, paint)

        //写文字
        paint.color = Color.GREEN
        paint.textSize = 120f //以px为单位
        canvas?.drawText("harvic\'s blog", baseLineX, baseLineY, paint)
    }
}