package com.kekshi.baseproject

import android.os.Bundle
import com.kekshi.baselib.base.BaseActivity
import kotlinx.android.synthetic.main.activity_test_view.*

class TestViewActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_view)
        btn.setOnClickListener {
            view.reset()
        }
        view.startAnimator()
    }
}