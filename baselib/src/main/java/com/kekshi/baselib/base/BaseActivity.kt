package com.kekshi.baselib.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.elvishew.xlog.XLog
import com.gyf.barlibrary.ImmersionBar
import com.kekshi.baselib.R
import com.kekshi.baselib.utils.ActivityCollector
import kotlinx.coroutines.*
import java.lang.ref.WeakReference

open class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private var weakRefActivity: WeakReference<Activity>? = null
    private var progressDialog: ProgressDialog? = null


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
        //取消协程
        cancel()
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun showToast(@StringRes msg: Int) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun showDialogs(title: String? = null, message: String = "") {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this, R.style.dialog_base).apply {
                if (title != null) {
                    setTitle(title)
                }
                setMessage(message)
                setCanceledOnTouchOutside(false)
//                setCancelable(false)
            }
        }
        progressDialog?.show()
    }

    fun hideDialogs() {
        progressDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    fun showAlertDialog(title: String, msg: String, confirm: () -> Unit) {
        showAlertDialog(title, msg, confirm, null)
    }

    fun showAlertDialog(title: String, msg: String, confirm: () -> Unit, cancel: (() -> Unit)?) {
        val dialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton("确定") { _, _ ->
                confirm.invoke()
            }
            .setNegativeButton("取消") { _, _ ->
                cancel?.invoke()
            }
            .create()
        dialog.show()
    }

    /**
     * 跳转到权限设置页面
     */
    fun toSettingPage(requestCode: Int) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", BaseApp.context.packageName, null)
        intent.data = uri
        startActivityForResult(intent, requestCode)
    }

    /**
     * 隐藏软键盘。
     */
    fun hideSoftKeyboard() {
        try {
            val view = currentFocus
            if (view != null) {
                val binder = view.windowToken
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        } catch (e: Exception) {
            XLog.e(e.message)
        }

    }

    /**
     * 不带参数的跳转
     */
    inline fun <reified T> toActivity() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }

    /**
     * 带参数的跳转
     */
    inline fun <reified T> toActivity(block: Intent.() -> Unit) {
        val intent = Intent(this, T::class.java)
        intent.block()
        startActivity(intent)
    }

    /**
     * 显示软键盘。
     */
    fun showSoftKeyboard(editText: EditText?) {
        try {
            if (editText != null) {
                editText.requestFocus()
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.showSoftInput(editText, 0)
            }
        } catch (e: Exception) {
            XLog.e(e.message)
        }
    }

    //在man协程中执行任务
    fun onCoroutineMain(action: () -> Unit) {
        launch {
            action()
        }
    }

    //在IO协程中执行任务
    fun onCoroutineIO(action: () -> Unit) {
        async {
            action()
        }
    }
}