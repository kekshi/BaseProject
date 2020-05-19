package com.kekshi.baseproject.widget.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.core.view.ViewConfigurationCompat
import kotlin.math.abs

class DependencyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var mSlop = 0
    private var mLastX = 0f
    private var mLastY = 0f

    init {
        isClickable = true
        mSlop = ViewConfiguration.get(this.context).scaledTouchSlop
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return super.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = event.x
                mLastY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = event.x - mLastX
                val deltaY = event.y - mLastY
                if (abs(deltaX) > mSlop || abs(deltaY) > mSlop) {
                    ViewCompat.offsetLeftAndRight(this, deltaX.toInt())
                    ViewCompat.offsetTopAndBottom(this, deltaY.toInt())
                    mLastX = event.x
                    mLastY = event.y
                }
            }
            MotionEvent.ACTION_UP -> {
                mLastX = event.x
                mLastY = event.y
            }
        }
        return true
    }
}