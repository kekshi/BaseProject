package com.kekshi.baselib.base

import android.os.Bundle

abstract class BaseMVPFragment<V : IView, P : BasePresenter<V>> : BaseFragment(), IView {

    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(onCreatorPresenter())
        presenter = onCreatorPresenter()
        presenter.initView(this as V)
    }

    abstract fun onCreatorPresenter(): P

    override fun onDestroy() {
        super.onDestroy()
    }
}