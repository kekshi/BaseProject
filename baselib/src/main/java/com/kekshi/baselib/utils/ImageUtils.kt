package com.kekshi.baselib.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageUtils {
    fun showImg(context: Context, url: String, iv: ImageView) {
        Glide.with(context).load(url).into(iv)
    }

    companion object {
        private var imageUtils: ImageUtils? = null
        fun getInstance(): ImageUtils {
            if (imageUtils == null) {
                imageUtils = ImageUtils()
            }
            return imageUtils!!
        }
    }
}