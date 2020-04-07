package com.kekshi.baseproject

import android.os.Bundle
import com.kekshi.baselib.base.BaseActivity

class TestViewActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_view)
//        btnResetBeiSaiEr.setOnClickListener {
//            beiSaiEr.reset()
//        }
//        beiSaiEr.startAnimator()
    }
}