package com.kekshi.baseproject.widget.zdyview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class XfermodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var mPaint: Paint = Paint()
    private var mBmp: Bitmap? = null
    private val bpWidth = 400
    private val bpHeight = 400
    private var dstBmp: Bitmap? = null
    private var srcBmp: Bitmap? = null

    init {
        mBmp = BitmapFactory.decodeResource(resources, com.kekshi.baseproject.R.drawable.dog)
//        setLayerType(LAYER_TYPE_SOFTWARE, null)//View 级别 关闭硬件加速

        dstBmp = makeDst(bpWidth, bpHeight)
        srcBmp = makeSrc(bpWidth, bpHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        testPorterDuffXfermode(canvas)
    }

    /**
     *  PorterDuff.Mode（18种）:
     *  Mode.CLEAR
     *  Mode.SRC
     *  Mode.DST
     *  Mode.SRC_OVER
     *  Mode.DST_OVER
     *  Mode.SRC_IN
     *  Mode.DST_IN
     *  Mode.SRC_OUT
     *  Mode.DST_OUT
     *  Mode.SRC_ATOP
     *  Mode.DST_ATOP
     *  Mode.XOR
     *  Mode.DARKEN
     *  Mode.LIGHTEN
     *  Mode.MULTIPLY
     *  Mode.SCREEN
     *  Mode.OVERLAY
     *  Mode.ADD
     */
    private fun testPorterDuffXfermode(canvas: Canvas?) {
        canvas ?: return
        //离屏绘制
        val layerID = canvas.saveLayer(
            0f,
            0f,
            width.toFloat() * 2,
            height.toFloat() * 2,
            mPaint,
            Canvas.ALL_SAVE_FLAG
        )
        canvas.drawBitmap(dstBmp, 0f, 0f, mPaint)
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
        canvas.drawBitmap(srcBmp, bpWidth / 2.toFloat(), bpHeight / 2.toFloat(), mPaint)
        mPaint.xfermode = null

        //重置（恢复到离屏前）
        canvas.restoreToCount(layerID)
    }

    private fun makeSrc(width: Int, height: Int): Bitmap? {
        val bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)

        p.color = Color.BLUE
        c.drawRect(0f, 0f, width.toFloat(), height.toFloat(), p)
        return bm
    }

    private fun makeDst(width: Int, height: Int): Bitmap? {
        val bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)

        p.color = Color.YELLOW
        c.drawOval(0f, 0f, width.toFloat(), height.toFloat(), p)
        return bm
    }
}