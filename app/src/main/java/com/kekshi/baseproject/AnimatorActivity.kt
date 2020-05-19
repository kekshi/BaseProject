package com.kekshi.baseproject

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.elvishew.xlog.XLog
import com.kekshi.baselib.base.BaseActivity
import kotlinx.android.synthetic.main.activity_animator.*
import kotlin.math.cos
import kotlin.math.sin

class AnimatorActivity : BaseActivity() {
    private var mIsMenuOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator)

        menu.setOnClickListener {
            if (!mIsMenuOpen) {
                openAnimate()
            } else {
                closeAnimate()
            }
        }
    }

    private fun closeAnimate() {
        mIsMenuOpen = false;
        doAnimateClose(item1, 0, 5, 300);
        doAnimateClose(item2, 1, 5, 300);
        doAnimateClose(item3, 2, 5, 300);
        doAnimateClose(item4, 3, 5, 300);
        doAnimateClose(item5, 4, 5, 300);
    }

    private fun openAnimate() {
        mIsMenuOpen = true;
        doAnimateOpen(item1, 0, 5, 300);
        doAnimateOpen(item2, 1, 5, 300);
        doAnimateOpen(item3, 2, 5, 300);
        doAnimateOpen(item4, 3, 5, 300);
        doAnimateOpen(item5, 4, 5, 300);
    }

    private fun doAnimateOpen(view: View?, index: Int, toal: Int, radius: Int) {
        if (view?.visibility != View.VISIBLE) {
            view?.visibility = View.VISIBLE
        }
        //根据度数得到弧度值
        val degree = Math.toRadians(90.0) / (toal - 1) * index
        val translationX = -(radius * sin(degree)).toFloat()
        val translationY = -(radius * cos(degree)).toFloat()

        val set = AnimatorSet()
        //包含平移、缩放和透明度动画
        set.playTogether(
            ObjectAnimator.ofFloat(view, "translationX", 0f, translationX),
            ObjectAnimator.ofFloat(view, "translationY", 0f, translationY),
            ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
            ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
            ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        );
        //动画周期为500ms
        set.setDuration(1 * 500).start()
        view?.setOnClickListener {
            showToast("Open你点击了${it?.id},位置是：$index")
        }
    }

    private fun doAnimateClose(view: View?, index: Int, toal: Int, radius: Int) {
        if (view?.visibility != View.VISIBLE) {
            view?.visibility = View.VISIBLE
        }
        //Math.PI不仅表示圆周率，也表示180度所对应的弧度。90度对应的为 Math.PI/2
//        Math.PI / 2 / (toal - 1)* index
        val degree = Math.PI / (2 * (toal - 1)) * index
        val translationX = -(radius * sin(degree)).toFloat()
        val translationY = -(radius * cos(degree)).toFloat()
        val set = AnimatorSet()
        //包含平移、缩放和透明度动画
        set.playTogether(
            ObjectAnimator.ofFloat(view, "translationX", translationX, 0f),
            ObjectAnimator.ofFloat(view, "translationY", translationY, 0f),
            ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f),
            ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f),
            ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        );
        //动画周期为500ms
        set.setDuration(1 * 500).start()

        view?.setOnClickListener {
            showToast("Close你点击了${it?.id},位置是：$index")
        }
    }
}