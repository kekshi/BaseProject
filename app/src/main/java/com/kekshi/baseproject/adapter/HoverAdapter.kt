package com.kekshi.baseproject.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kekshi.baseproject.R
import com.kekshi.baseproject.bean.UserBean

class HoverAdapter(data: MutableList<UserBean>) :
    BaseQuickAdapter<UserBean, BaseViewHolder>(R.layout.adapter_item_hover_user, data) {
    override fun convert(helper: BaseViewHolder, item: UserBean?) {
        helper.setText(R.id.user_name_tv, item?.userName)
    }
}