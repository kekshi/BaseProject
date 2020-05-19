package com.kekshi.baseproject.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kekshi.baseproject.R
import com.kekshi.baseproject.bean.TabsBean


class GridTestAdapter(data: MutableList<TabsBean>) :
    BaseQuickAdapter<TabsBean, BaseViewHolder>(R.layout.adapter_item_grid_test, data) {
    override fun convert(helper: BaseViewHolder, item: TabsBean?) {
        val textView = helper.getView<TextView>(R.id.tv_name)
        textView.text = item?.content
        textView.isSelected = item?.isSelect ?: false
    }

    fun setSelectItem(position: Int) {
        data.forEach {
            it.isSelect = false
        }
        data[position].isSelect = true
        notifyDataSetChanged()
    }
}