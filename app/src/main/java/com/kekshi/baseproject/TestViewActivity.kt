package com.kekshi.baseproject

import android.os.Bundle
import com.elvishew.xlog.XLog
import com.kekshi.baselib.base.BaseActivity
import com.kekshi.baseproject.zdylianbiao.LinkImpl

class TestViewActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_view)
        val all = LinkImpl<String>()
        XLog.d("增加之前的个数为：${all.size()}")
        all.add("Hello")
        all.add("Word")
        all.add("lalala")
        all.add("asdasd")
        XLog.d("增加之后的个数为：${all.size()}")
        all.toArray().forEach {
            XLog.d("添加的数据为：${it}")
        }

        all.remove("Word")
        all.remove(2)

        all.toArray().forEach {
            XLog.d("删除后的数据为：${it}")
        }
    }
}