package com.kekshi.baseproject.adapter

import android.util.SparseArray
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kekshi.baseproject.R
import com.kekshi.baseproject.widget.expandable.ExpandableTextView
import com.kekshi.baseproject.widget.expandable.ViewModelWithFlag


class TestAdapter(data: MutableList<ViewModelWithFlag>) :
    BaseQuickAdapter<ViewModelWithFlag, BaseViewHolder>(R.layout.adapter_item_test, data),
    LoadMoreModule {

    override fun convert(helper: BaseViewHolder, item: ViewModelWithFlag?) {

//        val collapsedTextView = helper.getView<CollapsedTextView>(R.id.tvContent2)
//        collapsedTextView.text = item
//        collapsedTextView.setExpandListener { view, isExpand ->
//            notifyItemChanged(helper.adapterPosition)
//        }

        val view = helper.getView<ExpandableTextView>(R.id.tv_item)
        view.bind(item)
        view.setContent(item?.title)
    }
}