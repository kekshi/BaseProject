package com.kekshi.baseproject.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kekshi.baseproject.R


class RvAdapter(private var dataList: List<String>, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("ddddd","onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_item_test, parent, false)
//        if (viewType == 1) {
//            return FootViewHolder(view)
//        }
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (dataList.isNotEmpty()) {
            this.dataList.size
        } else {
            0
        }
    }

    fun getDatas(): List<String> {
        return dataList
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            holder.textView.text = dataList[position] + position
        }
    }

    private inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tvContent)
    }

    private inner class FootViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tvContent)
    }
}