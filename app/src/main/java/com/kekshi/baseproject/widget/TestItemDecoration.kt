package com.kekshi.baseproject.widget

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kekshi.baselib.utils.DensityUtils

class TestItemDecoration(val color: Int) : RecyclerView.ItemDecoration() {
    private var mPaint: Paint = Paint()
    private var mLinePaint: Paint = Paint()
    private val mRadius = 20f
    private val mStrokeWidth = DensityUtils.dp2px(2f).toFloat()

    init {
        mPaint.color = color
        mPaint.style = Paint.Style.FILL
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = mStrokeWidth

        mLinePaint.color = Color.RED
        mLinePaint.style = Paint.Style.FILL
        mLinePaint.isAntiAlias = true
        mLinePaint.strokeWidth = mStrokeWidth
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        //类似时间轴的线
        parent.layoutManager?.let { layoutManager ->
            for (childIndex in 0 until childCount) {
                val childView = parent.getChildAt(childIndex)
                val leftWidth = layoutManager.getLeftDecorationWidth(childView)
                val cx = (leftWidth / 2).toFloat()
                val cy = (childView.top + childView.height / 2).toFloat()
                val lineX = cx
                //画圆上面的线
                if (childIndex > 0) {
                    val lineY = childView.top.toFloat()
                    val endLineY = cy - mRadius
                    c.drawLine(lineX, lineY, lineX, endLineY, mLinePaint)
                }
                //画圆
                c.drawCircle(cx, cy, mRadius, mPaint)
                //画圆下面的线
                if (childIndex < childCount - 1) {
                    val lineY = cy + mRadius
                    val endLineY = lineY + childView.height / 2
                    c.drawLine(lineX, lineY, lineX, endLineY, mLinePaint)
                }
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = 200
        outRect.top = DensityUtils.dp2px(1f)
    }
}