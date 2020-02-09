package com.kekshi.baseproject.activity.custom_view_example_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kekshi.baseproject.R
import com.kekshi.baseproject.adapter.HoverAdapter
import com.kekshi.baseproject.bean.UserBean
import com.kekshi.baseproject.utils.CharacterParser
import com.kekshi.baseproject.utils.PinyinComparator
import com.kekshi.baseproject.widget.HoverItemDecoration
import kotlinx.android.synthetic.main.activity_hover_item.*
import java.util.*

class HoverItemActivity : AppCompatActivity() {

    private lateinit var adapter: HoverAdapter

    private var userBeans: MutableList<UserBean> = ArrayList()

    private val names = mutableListOf<String>("阿妹", "打黑牛", "张三", "李四", "王五", "田鸡", "孙五")

    /**
     * 汉字转换成拼音的类
     */
    private lateinit var characterParser: CharacterParser
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private lateinit var pinyinComparator: PinyinComparator

    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hover_item)

        characterParser = CharacterParser.getInstance()
        pinyinComparator = PinyinComparator()

        userBeans = filledData(getData())

        layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
        //一行代码实现吸顶悬浮的效果
        recycler_view.addItemDecoration(
            HoverItemDecoration(
                this,
                HoverItemDecoration.BindItemTextCallback { position ->
                    //悬浮的信息
                    userBeans[position].sortLetters
                })
        )

        adapter = HoverAdapter(userBeans)

        recycler_view.adapter = adapter

        initIndexView()
    }

    private fun initIndexView() {
        index_view.setShowTextDialog(show_text_dialog)
        index_view.setOnTouchingLetterChangedListener { letter ->
            val position = getPositionForSection(letter)
            if (position != -1) {
                layoutManager.scrollToPositionWithOffset(position, 0)
                // true，先添加的item会被顶上去，最新添加的item每次都会显示在最下面
                layoutManager.stackFromEnd = false
            }
        }
    }

    private fun getPositionForSection(letter: String?): Int {
        for ((index, value) in userBeans.withIndex()) {
            val sortLetters = userBeans[index].sortLetters
            if (sortLetters == letter) {
                return index
            }
        }
        return -1
    }

    private fun getData(): MutableList<UserBean> {
        val userBeans = ArrayList<UserBean>()
        for (i in 0..49) {
            val userBean = UserBean()
            userBean.userName = names[i % 7]
            userBeans.add(userBean)
        }
        return userBeans
    }

    private fun filledData(sortList: MutableList<UserBean>): MutableList<UserBean> {
        for (i in sortList.indices) {
            if ("" == sortList[i].userName) {
                sortList[i].sortLetters = "#"
            } else {
                // 汉字转换成拼音
                val pinyin = characterParser.getSelling(sortList[i].userName)
                val sortString = pinyin.substring(0, 1).toUpperCase()

                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]".toRegex())) {
                    sortList[i].sortLetters = sortString.toUpperCase()
                } else {
                    sortList[i].sortLetters = "#"
                }
            }
        }
        // 根据a-z进行排序源数据
        Collections.sort(sortList, pinyinComparator)

        return sortList
    }
}