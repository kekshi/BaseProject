package com.kekshi.baselib.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.kekshi.baselib.utils.DensityUtils
import kotlin.math.abs

/**
 * 绘制多行文字
 */
class ImageBeginTextView : View {
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initPaint()
    }


    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val txtStr = "人之初，性本善。性相近，习相远。苟不教，性乃迁。教之道，贵以专。" +
            "昔孟母，择邻处。子不学，断机杼。窦燕山，有义方。教五子，名俱扬。养不教，父之过。"

    // 图片左右padding值
    private var mPadding = DensityUtils.dp2px(5f)
    // 文字大小
    private var txtSize = DensityUtils.sp2px(16f)
    // 图片宽度(绘制文字的时候使用)
    private var imgWidth = DensityUtils.dp2px(20f)

    // 声明一个长度为1的Float数组
    private val measuredWidth = FloatArray(1)

    private var fontMetrics: Paint.FontMetrics? = null

    private fun initPaint() {
        mPaint.textSize = txtSize.toFloat()
        fontMetrics = mPaint.fontMetrics
        mPaint.strokeWidth = 5f
        mPaint.color = Color.RED
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // 基线Y轴
        var verticalOffset = abs(fontMetrics?.top ?: mPaint.ascent())

        var start = 0
        while (start < txtStr.length) {

            // 首行需要减掉图片宽度和开始padding,图片宽度，图片右边padding，文字右边padding;
            val maxWidth = if (start == 0)
                width - mPadding - imgWidth - mPadding - mPadding
            else
                width - mPadding * 2

            // 返回一个长度，本行绘制了多少个字
            val breakTextCount = mPaint.breakText(
                txtStr,
                true,     // 是否正向绘制
                maxWidth.toFloat(),                   // 最大绘制宽度
                measuredWidth               // 绘制本行文字的宽度存在这个数组中
            )

            /*
            绘制文字
            text：  需要绘制的文本
            start:  从哪个位置开始绘制
            end:    绘制到哪里，最多绘制到最后一位
            x,y:    如果是第一行，从图片后边开始绘制
            paint:  画笔
             */
            canvas?.drawText(
                txtStr,
                start,
                if (start + breakTextCount > txtStr.length) txtStr.length else start + breakTextCount,   //
                if (start == 0) {
                    (mPadding + imgWidth + mPadding).toFloat()
                } else mPadding.toFloat(),
                verticalOffset,
                mPaint
            )
            start += breakTextCount
            verticalOffset += mPaint.fontSpacing
        }

    }
}
