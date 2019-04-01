package com.kekshi.baselib.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.kekshi.baselib.R;


public class LoadingDialog extends Dialog {

    private static LoadingDialog mLoadingProgress;

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    public static LoadingDialog showProgress(Context context, CharSequence message, boolean isCanCancel) {
        mLoadingProgress = new LoadingDialog(context, R.style.loading_dialog);//自定义style主要让背景变成透明并去掉标题部分

        //触摸外部无法取消,必须（根据自己需要吧）
        mLoadingProgress.setCanceledOnTouchOutside(false);

        mLoadingProgress.setTitle("");
        mLoadingProgress.setContentView(R.layout.loading_layout);
        mLoadingProgress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        if (message == null || TextUtils.isEmpty(message)) {
            mLoadingProgress.findViewById(R.id.loading_tv).setVisibility(View.GONE);
        } else {
            TextView tv = (TextView) mLoadingProgress.findViewById(R.id.loading_tv);
            tv.setText(message);
        }
        //按返回键响应是否取消等待框的显示
        mLoadingProgress.setCancelable(isCanCancel);

        mLoadingProgress.show();

        return mLoadingProgress;
    }

    public static LoadingDialog showProgress(Context context, boolean isCanCancel) {
        return showProgress(context, "", isCanCancel);
    }

    public static LoadingDialog showProgress(Context context, String message) {
        return showProgress(context, message, true);
    }

    public static LoadingDialog showProgress(Context context) {
        return showProgress(context, true);
    }


    public static void dismissProgress() {
        if (mLoadingProgress != null && mLoadingProgress.isShowing()) {
            mLoadingProgress.dismiss();
        }
    }
}