package com.kekshi.baseproject.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.elvishew.xlog.XLog
import com.kekshi.baselib.base.BaseActivity
import com.kekshi.baselib.base.BaseApp.Companion.context
import com.kekshi.baseproject.R
import com.kekshi.baseproject.TestActivity
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : BaseActivity() {

    private val NOTIFICATION_ID = 1
    private val CHANNEL_ID = "default"
    private val CHANNEL_Name = "rabbitPro"
    var requestCode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        btnSend.setOnClickListener {
            onSendNotification()
        }
    }

    private fun onSendNotification() {
        val notifyMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            onCreateNotificationChannel(notifyMgr)
        }
        val requestCode = requestCode++
        XLog.d("requestCode is :$requestCode")
        val pendingIntent = PendingIntent.getActivity(
            context, 0,
            Intent(this, TestActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_small_launcher)
            .setContentTitle("我是通知title")
            .setContentText("我是通知内容$requestCode")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        val tag = "tag-$requestCode"
        XLog.d("tag is :$tag")
        notifyMgr.notify(tag, NOTIFICATION_ID, builder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onCreateNotificationChannel(notifyMgr: NotificationManager) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_Name, NotificationManager.IMPORTANCE_HIGH
        )
        notifyMgr.createNotificationChannel(channel)
    }
}