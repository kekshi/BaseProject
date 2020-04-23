package com.kekshi.baseproject

import android.os.Bundle
import android.util.Log
import com.kekshi.baselib.base.BaseActivity
import kotlinx.android.synthetic.main.activity_test_view.*

class TestViewActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_view)

//        testThread()
        testAnimator()
    }

    private fun testAnimator() {
//        btnResetBeiSaiEr.setOnClickListener {
//            val animator = ValueAnimator.ofObject(CharEvaluator(), 'A', 'Z')
//            animator.addUpdateListener {
//                val text = it.animatedValue as Char
//                btnResetBeiSaiEr.text = text.toString()
//            }
//            animator.duration = 1000
//            animator.interpolator = AccelerateInterpolator()//加速插值器
//            animator.start()
//        }
        btnResetBeiSaiEr.setOnClickListener {
            pointView.doPointAnim()
        }
    }

    fun maipiao() {
        synchronized(this) {

        }
    }

    fun testThread() {
        val msg = Message()
        Thread(Producer(msg)).start()//启动生产者线程
        Thread(Consumer(msg)).start()//启动消费者线程
    }
}

//生产者
class Producer(val msg: Message) : Runnable {

    override fun run() {
        for (position in 0 until 100) {
            Thread.sleep(500)
            if (position % 2 == 0) {
                msg.set("张三", "超级大帅哥")
            } else {
                msg.set("李四", "猥琐王")
            }
//            Log.d("ddddd", "name:${msg.name},content:${msg.content}")
        }
    }

}

//消费者
class Consumer(val msg: Message) : Runnable {

    override fun run() {
        for (position in 0 until 100) {
            Thread.sleep(100)
            Log.d("ddddd", "msg is :${msg.get()}")
        }
    }
}

class Message : Object() {
    private var name: String? = null
    private var content: String? = null
    private var flag = true//表示生产的形式。true表示允许生产，不允许消费；false表示允许消费，不允许生产。
    @Synchronized
    fun set(name: String, content: String) {
        if (!flag) {
            super.wait()
        }
        Thread.sleep(100)
        this.name = name
        this.content = content

        flag = false
        notify()
    }

    @Synchronized
    fun get(): String {
        if (flag) {
            super.wait()
        }
        try {
            return "$name-$content"
        } finally {
            flag = true
            notify()
        }
    }
}
