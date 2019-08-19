package com.kekshi.baseproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.kekshi.baselib.base.BaseFragment
import com.kekshi.baselib.view.SwipeCaptcha
import kotlinx.android.synthetic.main.fragment_one_layout.*


class OneFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.kekshi.baseproject.R.layout.fragment_one_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipeCaptchaView.onCaptchaMatchCallback = object : SwipeCaptcha.OnCaptchaMatchCallback {
            override fun matchSuccess(rxSwipeCaptcha: SwipeCaptcha?) {
                showToast("验证通过！")
                //swipeCaptcha.createCaptcha();
                dragBar.isEnabled = false
            }

            override fun matchFailed(rxSwipeCaptcha: SwipeCaptcha?) {
                showToast("验证失败:拖动滑块将悬浮头像正确拼合")
                swipeCaptchaView.resetCaptcha()
                dragBar.progress = 0
            }
        }
        dragBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                swipeCaptchaView.setCurrentSwipeValue(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //随便放这里是因为控件
                dragBar.max = swipeCaptchaView.maxSwipeValue
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                swipeCaptchaView.matchCaptcha()
            }
        })
        btnChange.setOnClickListener {
            swipeCaptchaView.createCaptcha()
            dragBar.isEnabled = true
            dragBar.progress = 0
        }
    }

    override fun lazyLoad() {
    }
}