package com.kekshi.baselib.base

import android.os.Bundle

abstract class BaseMVPActivity<VIEW : IView, PRESENTER : BasePresenter<VIEW>> : BaseActivity(), IView {

    lateinit var presenter: PRESENTER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(onCreatorPresenter())
        presenter = onCreatorPresenter()
        presenter.initView(this)
    }

    abstract fun onCreatorPresenter(): PRESENTER

    override fun onDestroy() {
        super.onDestroy()
    }
}