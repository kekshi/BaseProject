package com.kekshi.baselib.base

import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*

abstract class BasePresenter<V : IView> : IPresenter<V>, CoroutineScope by MainScope() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    lateinit var view: V

    override fun initView(view: V) {
        this.view = view
    }

    override fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event) {

    }

    override fun onCreate(owner: LifecycleOwner) {

    }

    //在man协程中执行任务
    protected fun onCoroutineMain(action: () -> Unit) {
        launch {
            action()
        }
    }

    //在IO协程中执行任务
    protected fun onCoroutineIO(action: () -> Unit) {
        async {
            action()
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        compositeDisposable.clear()
        //取消协程任务
        cancel()
    }

    abstract fun onViewReady()

    fun showToast(msg: String) {
        Toast.makeText(BaseApp.context, msg, Toast.LENGTH_SHORT).show()
    }
}