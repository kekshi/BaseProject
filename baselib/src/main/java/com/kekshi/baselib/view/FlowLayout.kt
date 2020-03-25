package com.kekshi.baselib.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import kotlin.math.max

class FlowLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var lineWidth = 0
        var lineHeight = 0
        var width = 0//控件宽
        var height = 0//控件高

        val measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val measuredHeight = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        for (index in 0 until childCount) {
            val childView = getChildAt(index)
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
            //Margin 是在控件之外的，padding是在控件内的
            val marginLayoutParams = childView.layoutParams as MarginLayoutParams
            val chidlWidth =
                childView.measuredWidth + marginLayoutParams.marginStart + marginLayoutParams.marginEnd
            val chidlHeight =
                childView.measuredHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin

            if (lineWidth + chidlWidth > measuredWidth) {
                //大于控件宽度时，换行，取宽度最宽的那个，高度累加。
                width = max(lineWidth, chidlWidth)
                height += lineHeight
                //换行了，重置行宽、行高为当前子控件宽高
                lineWidth = chidlWidth
                lineHeight = chidlHeight
            } else {
                //不大于控件宽度时，不换行，宽度累加，高度取最高的那个。
                lineWidth += chidlWidth
                lineHeight = max(lineHeight, chidlHeight)
            }

            if (index == childCount - 1) {
                width = max(width, chidlWidth)
                height += chidlHeight
            }
            setMeasuredDimension(
                if (widthMode == MeasureSpec.EXACTLY) measuredWidth else width,
                if (heightMode == MeasureSpec.EXACTLY) measuredHeight else height
            )
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        var left = 0
        var top = 0
        var lineWidth = 0
        var lineHeight = 0

        for (index in 0 until childCount) {
            val childView = getChildAt(index)

            val marginLayoutParams = childView.layoutParams as MarginLayoutParams
            val chidlWidth =
                childView.measuredWidth + marginLayoutParams.marginStart + marginLayoutParams.marginEnd
            val chidlHeight =
                childView.measuredHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin
            //大于就换行，不大于就不换行
            if (lineWidth + chidlWidth > measuredWidth) {
                //如果换行,当前控件将跑到下一行，从最左边开始，所以left就是0，而top则需要加上上一行的行高，才是这个控件的top点;
                top += lineHeight
                left = 0

                lineWidth = chidlWidth
                lineHeight = chidlHeight
            } else {
                lineWidth += chidlWidth
                lineHeight = max(lineHeight, chidlHeight)
            }
            //计算childView的left,top,right,bottom
            val lc = left + marginLayoutParams.leftMargin
            val tc = top + marginLayoutParams.topMargin
            val rc = lc + childView.measuredWidth
            val bc = tc + childView.measuredHeight
            childView.layout(lc, tc, rc, bc)
            //将left置为下一子控件的起始点
            left += chidlWidth
        }
    }

    //添加 Margin
    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
    }
}