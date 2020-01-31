package com.kekshi.baseproject.activity.custom_view_example_activity

import android.os.Bundle
import android.util.Log
import com.kekshi.baselib.base.BaseActivity
import com.kekshi.baseproject.R
import kotlinx.android.synthetic.main.activity_progress_bar.*

class ProgressBarActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_bar)
        circleProgressBarView.setProgressWithAnimation(60f)
        circleProgressBarView.setProgressListener {
            Log.d("dddd", "当前进度：$it")
        }
        circleProgressBarView.startProgressAnimation()

        horizontalProgressBar.setProgressWithAnimation(60f).setProgressListener {

        }
    }
}