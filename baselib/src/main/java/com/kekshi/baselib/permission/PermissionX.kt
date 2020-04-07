package com.kekshi.baselib.permission

import androidx.fragment.app.FragmentActivity

/**
 *请求权限的封装
 * 调用示例：
 * PermissionX.request(this,Manifest.permission.READ_PHONE_STATE){ allGranted,deniedList->
 *      if(allGranted){
 *          //有权限
 *      }else{
 *      }
 * }
 */
object PermissionX {
    private const val TAG = "InvisibleFragment"

    //*permission 是将数组转换为可变参数进行传递
    fun request(
        activity: FragmentActivity,
        vararg permission: String,
        callback: PermissionCallback
    ) {
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment = if (existedFragment != null) {
            existedFragment as InvisibleFragment
        } else {
            val invisibleFragment = InvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        fragment.requestNow(callback, *permission)
    }
}