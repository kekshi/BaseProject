package com.kekshi.baselib.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment


abstract class BaseFragement : Fragment() {

    /**
     * View有没有加载过
     */
    protected var isViewInitiated: Boolean = false
    /**
     * 页面是否可见
     */
    protected var isVisibleToUser: Boolean = false
    /**
     * 是不是加载过
     */
    protected var isDataInitiated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
        loadData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        loadData()
    }

    /**
     *懒加载，Fragment可见的时候调用这个方法，而且只调用一次
     */
    abstract fun lazyLoad()

    private fun loadData() {
        if (isVisibleToUser && isViewInitiated && !isDataInitiated) {
            isDataInitiated = true
            lazyLoad()
        }
    }

    fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun showToast(@StringRes msg: Int) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}