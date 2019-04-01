package com.kekshi.baselib.base

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gyf.barlibrary.ImmersionBar
import com.kekshi.baselib.R
import com.kekshi.baselib.utils.ActivityCollector
import java.lang.ref.WeakReference

open class BaseActivity : AppCompatActivity() {
    private var weakRefActivity: WeakReference<Activity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weakRefActivity = WeakReference(this)
        ActivityCollector.add(weakRefActivity)

        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar()
        }
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected fun isImmersionBarEnabled(): Boolean {
        return true
    }

    protected fun initImmersionBar() {
        //在BaseActivity里初始化
        ImmersionBar.with(this).navigationBarColor(R.color.colorPrimary).init()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.remove(weakRefActivity)
        if (isImmersionBarEnabled()) {
            ImmersionBar.with(this).destroy()
        }
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}