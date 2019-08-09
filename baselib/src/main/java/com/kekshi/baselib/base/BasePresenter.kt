package com.kekshi.baselib.base

import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : IView> : IPresenter<V> {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    lateinit var view: V

    override fun initView(view: V) {
        this.view = view
    }

    override fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event) {

    }

    override fun onCreate(owner: LifecycleOwner) {

    }

    override fun onDestroy(owner: LifecycleOwner) {
        compositeDisposable.clear()
    }

    abstract fun onViewReady()

    fun showToast(msg: String) {
        Toast.makeText(BaseApp.context, msg, Toast.LENGTH_SHORT).show()
    }
}