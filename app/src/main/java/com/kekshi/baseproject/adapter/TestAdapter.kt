package com.kekshi.baseproject.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kekshi.baseproject.R

class TestAdapter(data: MutableList<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.adapter_item_test, data), LoadMoreModule {
    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.setText(R.id.tvContent, item + "${helper.adapterPosition}")
    }
}