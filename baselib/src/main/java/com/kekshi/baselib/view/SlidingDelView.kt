package com.kekshi.baselib.view

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.kekshi.baselib.utils.DensityUtils

/**
 * 侧滑删除控件
 *
 * <com.imio.mine.view.SlidingDelView xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:tools="http://schemas.android.com/tools"
android:layout_width="match_parent"
android:layout_height="72dp">

<LinearLayout
android:layout_width="wrap_content"
android:layout_height="match_parent"
android:orientation="horizontal">

<RelativeLayout
android:id="@+id/rl_content"
android:layout_width="wrap_content"
android:layout_height="match_parent">

<TextView
android:id="@+id/tv_device_name"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:layout_marginStart="30dp"
android:layout_marginTop="15dp"
android:textColor="@color/white"
android:textSize="14sp"
tools:text="小喆的锤子R1" />

<TextView
android:id="@+id/tv_login_time"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:layout_alignStart="@+id/tv_device_name"
android:layout_below="@+id/tv_device_name"
android:layout_marginTop="5dp"
android:textColor="@color/font_b8"
android:textSize="12sp"
tools:text="2017-03-01 13:00" />

<TextView
android:id="@+id/tv_device_type"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:layout_alignTop="@id/tv_login_time"
android:layout_below="@+id/tv_device_name"
android:layout_marginStart="20dp"
android:layout_toEndOf="@id/tv_login_time"
android:textColor="@color/font_b8"
android:textSize="12sp"
tools:text="坚果R1" />
</RelativeLayout>

<TextView
android:id="@+id/tv_delete"
android:layout_width="72dp"
android:layout_height="72dp"
android:background="@color/red"
android:gravity="center"
android:text="@string/delete"
android:textColor="@color/white"
android:textSize="14sp" />
</LinearLayout>

</com.imio.mine.view.SlidingDelView>
 */
class SlidingDelView : HorizontalScrollView {

    private var mScale: DisplayMetrics
    private var mDelBtnWidth: Int

    constructor(ctx: Context) : this(ctx, null)

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr) {
        mScale = context.resources.displayMetrics
        mDelBtnWidth = DensityUtils.dp2px(72f)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        val layout: LinearLayout = getChildAt(0) as LinearLayout
        val layoutLeft = layout.getChildAt(0)
        val layoutRight = layout.getChildAt(1)

        layout.layout(layout.left, layout.top, layout.left + measuredWidth + mDelBtnWidth, layout.bottom)
        layoutLeft.layout(layoutLeft.left, layoutLeft.top, layoutLeft.left + measuredWidth, layout.bottom)
        layoutRight.layout(layoutLeft.left + measuredWidth, layoutRight.top, layoutLeft.left + measuredWidth + mDelBtnWidth, layoutRight.bottom)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (ev!!.action == MotionEvent.ACTION_DOWN) {
            return true
        }
        if (ev.action == MotionEvent.ACTION_CANCEL || ev.action == MotionEvent.ACTION_UP) {
            val range = 70
            if (scrollX < mDelBtnWidth - range) {
                smoothScrollTo(0, 0)
            } else {
                smoothScrollTo(mDelBtnWidth, 0)
            }
            return true
        }
        return super.onTouchEvent(ev)
    }

    fun reset() {
        smoothScrollTo(0, 0)
    }

}