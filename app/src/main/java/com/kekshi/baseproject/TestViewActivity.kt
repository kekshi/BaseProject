package com.kekshi.baseproject

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kekshi.baselib.base.BaseActivity
import com.kekshi.baselib.utils.DensityUtils
import com.kekshi.baselib.utils.ImageUtils
import com.kekshi.baselib.view.CrystalRangeSeekbar
import com.kekshi.baselib.view.EvenItemDecoration
import com.kekshi.baseproject.adapter.GridTestAdapter
import com.kekshi.baseproject.adapter.TestAdapter
import com.kekshi.baseproject.bean.TabsBean
import com.kekshi.baseproject.widget.expandable.ViewModelWithFlag
import kotlinx.android.synthetic.main.activity_test_view.*

class TestViewActivity : BaseActivity() {
    private val yourText =
        "我所认识的中国，强大、友好我所认识的中国，强大、友好。@奥特曼 “一带一路”经济带带动了沿线国家的经济发展，促进我国与他国的友好往来和贸易发展，可谓“双赢”。http://www.baidu.com 自古以来，中国以和平、友好的面孔示人。汉武帝派张骞出使西域，开辟丝绸之路，增进与西域各国的友好往来。http://www.baidu.com 胡麻、胡豆、香料等食材也随之传入中国，汇集于中华美食。@RNG 漠漠古道，驼铃阵阵，这条路奠定了“一带一路”的基础，让世界认识了中国。"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_view)

//        testThread()
//        testAnimator()
//        addView()
        testRangeSeekBar()
    }

    private fun testRangeSeekBar() {
        // set properties
        rangeSeekbar6
            .setCornerRadius(10f)
            .setBarColor(Color.parseColor("#33FD5056"))
            .setBarHighlightColor(Color.parseColor("#FD5056"))
            .setMinValue(0f)
            .setMaxValue(2000f)
            .setSteps(20f)
            .setMinStartValue(100f)
            .setMaxStartValue(2000f)
            .setLeftThumbDrawable(R.drawable.icon_range)
            .setLeftThumbHighlightDrawable(R.drawable.icon_range)
            .setRightThumbDrawable(R.drawable.icon_range)
            .setRightThumbHighlightDrawable(R.drawable.icon_range)
            .setDataType(CrystalRangeSeekbar.DataType.INTEGER)
            .apply()

        // set listener
        rangeSeekbar6.setOnRangeSeekbarChangeListener { minValue, maxValue ->
            textMin6.setText(minValue.toString())
            textMax6.setText(maxValue.toString())
        }
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

        recycler_view_tabs.layoutManager = GridLayoutManager(this, 4)
        recycler_view_tabs.addItemDecoration(EvenItemDecoration(DensityUtils.dp2px(8f), 4))
        val gridTestAdapter = GridTestAdapter(getDataTabs())
        recycler_view_tabs.adapter = gridTestAdapter
        gridTestAdapter.setOnItemClickListener { adapter, view, position ->
            gridTestAdapter.setSelectItem(position)
            showToast("您点击了${gridTestAdapter.getItem(position)!!.content}")
        }


        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        val testAdapter = TestAdapter(getData())
        recycler_view.adapter = testAdapter
        testAdapter.setOnItemChildClickListener { adapter, view, position ->
            showToast("您点击了第" + position + "个Item")
        }
    }

    private fun getDataTabs(): MutableList<TabsBean> {
        return mutableListOf<TabsBean>().apply {
            add(TabsBean("学区房", true))
            add(TabsBean("婚房"))
            add(TabsBean("房贷"))
            add(TabsBean("商圈"))
            add(TabsBean("新房"))
            add(TabsBean("政策"))
            add(TabsBean("户型"))
            add(TabsBean("法务"))
        }
    }

    private fun addView() {
        val arrayListOf = arrayListOf<View>()
        ll.removeAllViews()
//        for (i in 5 downTo 0) {
        for (i in 0..5) {
            val imageView = ImageView(this)
            val layoutParams = LinearLayout.LayoutParams(
                DensityUtils.dp2px(25f),
                DensityUtils.dp2px(25f)
            )
//            imageView.setImageResource(R.mipmap.ic_launcher)
            if (i == 0) {
//                imageView.setPadding(0, 0, DensityUtils.dp2px(-14f), 0)
            } else {
//                imageView.marginLeft = DensityUtils.dp2px(-14f)
//                layoutParams.setMargins(DensityUtils.dp2px(-14f), 0, 0, 0)
//                layoutParams.marginEnd = DensityUtils.dp2px(-14f)
//                imageView.setPadding(DensityUtils.dp2px(-14f), 0, 0, 0)
//                imageView.adjustViewBounds = true
                val drawable = imageView.drawable
//                drawable.bounds = Rect(0, 0, DensityUtils.dp2px(-14f), 0)
            }
            ImageUtils.loadImageViewCircleCorners(
                this,
                imageView,
                R.mipmap.default_avatar,
                R.mipmap.default_avatar
            )
            arrayListOf.add(imageView)
//            ll.addView(imageView, layoutParams)

        }
        da_view.addDatas(arrayListOf)
    }

    private fun getData(): MutableList<ViewModelWithFlag> {
        val list = mutableListOf<ViewModelWithFlag>()
        for (i in 0..49) {
            list.add(ViewModelWithFlag("第" + (i + 1) + "条数据：" + yourText))
        }
        return list
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
