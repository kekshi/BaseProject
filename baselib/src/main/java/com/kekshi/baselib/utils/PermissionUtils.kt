package com.kekshi.baselib.utils

import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


object PermissionUtils {
    /**
     * 检查是否授权改权限
     * */
    fun checkSelfPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private var mRequestCode = -1

    fun requestPermissionsResult(
        activity: Activity,
        requestCode: Int,
        permission: Array<String>,
        callback: OnPermissionListener
    ) {
        requestPermissions(activity, requestCode, permission, callback)
    }

    fun requestPermissionsResult(
        fragment: Fragment,
        requestCode: Int,
        permission: Array<String>,
        callback: OnPermissionListener
    ) {
        requestPermissions(fragment, requestCode, permission, callback)
    }

    /**
     * 请求权限处理
     * @param `any`        activity or fragment
     * @param requestCode   请求码
     * @param permissions   需要请求的权限
     * @param callback      结果回调
     */
    @TargetApi(Build.VERSION_CODES.M)
    private fun requestPermissions(
        any: Any,
        requestCode: Int,
        permissions: Array<String>,
        callback: OnPermissionListener
    ) {

        checkCallingObjectSuitability(any)
        mOnPermissionListener = callback

        if (checkPermissions(getContext(any), *permissions)) {
            mOnPermissionListener?.onPermissionGranted()
        } else {
            val deniedPermissions = getDeniedPermissions(getContext(any), *permissions)
            if (deniedPermissions.size > 0) {
                mRequestCode = requestCode

                if (any is Activity) {
                    any.requestPermissions(
                        deniedPermissions
                            .toTypedArray(), requestCode
                    )
                } else if (any is Fragment) {
                    any.requestPermissions(
                        deniedPermissions
                            .toTypedArray(), requestCode
                    )
                }
            }
        }
    }

    /**
     * 获取上下文
     */
    private fun getContext(any: Any): Context {
        val context: Context
        if (any is Fragment) {
            context = any.activity!!
        } else {
            context = any as Activity
        }
        return context
    }

    /**
     * 请求权限结果，对应onRequestPermissionsResult()方法。
     */
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == mRequestCode) {
            if (verifyPermissions(grantResults)) {
                mOnPermissionListener?.onPermissionGranted()
            } else {
                mOnPermissionListener?.onPermissionDenied()
            }
        }
    }

    /**
     * 显示提示对话框
     */
    fun showTipsDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("提示标题")
            .setMessage("请打开相应的权限")
            .setNegativeButton("取消", null)
            .setPositiveButton("确定", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    startAppSettings(context)
                }
            }).show()
    }

    /**
     * 启动当前应用设置页面
     */
    fun startAppSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.setData(Uri.parse("package:" + context.packageName))
        context.startActivity(intent)
    }

    /**
     * 验证权限是否都已经授权
     */
    private fun verifyPermissions(grantResults: IntArray): Boolean {
        // 如果请求被取消，则结果数组为空
        if (grantResults.size <= 0)
            return false

        // 循环判断每个权限是否被拒绝
        for (grantResult in grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * 获取权限列表中所有需要授权的权限
     * @param context       上下文
     * @param permissions   权限列表
     * @return
     */
    private fun getDeniedPermissions(context: Context, vararg permissions: String): List<String> {
        val deniedPermissions = arrayListOf<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_DENIED
            ) {
                deniedPermissions.add(permission)
            }
        }
        return deniedPermissions
    }

    /**
     * 检查所传递对象的正确性
     * @param `any` 必须为 activity or fragment
     */
    private fun checkCallingObjectSuitability(any: Any?) {
        if (any == null) {
            throw NullPointerException("Activity or Fragment should not be null")
        }

        val isActivity = any is Activity
        val isAppFragment = any is Fragment

        require(isActivity || isAppFragment) { "Caller must be an Activity or a Fragment" }
    }

    /**
     * 检查所有的权限是否已经被授权
     * @param permissions 权限列表
     * @return
     */
    fun checkPermissions(context: Context, vararg permissions: String): Boolean {
        if (isOverMarshmallow()) {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * 判断当前手机API版本是否 >= 6.0
     */
    fun isOverMarshmallow(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    interface OnPermissionListener {
        fun onPermissionGranted()
        fun onPermissionDenied()
    }

    private var mOnPermissionListener: OnPermissionListener? = null
}