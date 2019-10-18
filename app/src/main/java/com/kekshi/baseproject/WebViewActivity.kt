package com.kekshi.baseproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.kekshi.baselib.base.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        initView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                pbLoad.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
//                toolbar.title = webView.title
                pbLoad.visibility = View.GONE
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                pbLoad.progress = newProgress
            }
        }
        with(webView.settings) {
            javaScriptEnabled = true
            // 是否支持缩放
            setSupportZoom(true)
            // 存储(storage)
//            domStorageEnabled = true
            // 设置出现缩放工具
            builtInZoomControls = true;
            //扩大比例的缩放
            useWideViewPort = true
            //自适应屏幕
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
            // 当页面宽度大于WebView宽度时，缩小使页面宽度等于WebView宽度
            loadWithOverviewMode = true
        }
        webView.loadUrl(intent.getStringExtra(URL))
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            finish()
        }
    }

    companion object {
        private const val TITLE = "title"
        private const val URL = "url"

        fun toActivity(context: Context, title: String, url: String) {
            val intent = Intent(context, WebViewActivity::class.java).apply {
                putExtra(TITLE, title)
                putExtra(URL, url)
            }
            context.startActivity(intent)
        }
    }
}