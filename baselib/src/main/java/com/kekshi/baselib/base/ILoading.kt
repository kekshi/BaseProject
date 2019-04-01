package com.kekshi.baselib.base

import android.app.Activity
import com.kekshi.baselib.view.LoadingDialog

interface ILoading {

    var loading: LoadingDialog?

    fun showLoading(activity: Activity, content: String) {
        loading = LoadingDialog.showProgress(activity, content)
    }

    fun hideLoading(activity: Activity) {
        if (loading != null &&
            loading!!.isShowing &&
            !(activity.isDestroyed || activity.isFinishing)
        ) {
            loading!!.dismiss()
        }
    }

    fun setLoadingVisible(loadingVisible: Boolean)
}