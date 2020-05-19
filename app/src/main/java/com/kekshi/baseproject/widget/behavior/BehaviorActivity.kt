package com.kekshi.baseproject.widget.behavior

import android.os.Bundle
import com.kekshi.baselib.base.BaseActivity
import com.kekshi.baselib.utils.DensityUtils
import com.kekshi.baselib.utils.ImageUtils
import com.kekshi.baseproject.R
import kotlinx.android.synthetic.main.activity_behavior.*

class BehaviorActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_behavior)

        val url =
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1272000576,1167023839&fm=26&gp=0.jpg"
        ImageUtils.loadImageViewCircleCorners(this, iv_test, url, R.drawable.dog)
    }
}