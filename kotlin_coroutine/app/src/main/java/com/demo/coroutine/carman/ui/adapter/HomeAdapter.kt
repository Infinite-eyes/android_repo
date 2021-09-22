package com.demo.coroutine.carman.ui.adapter

import com.demo.coroutine.carman.base.BaseAdapter
import com.demo.coroutine.carman.interf.ItemClickListener
import com.demo.coroutine.databinding.ItemHomeBinding

class HomeAdapter(private val listener: ItemClickListener<String>) :
    BaseAdapter<String, ItemHomeBinding>() {

    override fun ItemHomeBinding.setListener() {
        itemClickListener = listener
    }

    override fun ItemHomeBinding.onBindViewHolder(bean: String, position: Int) {
        this.bean = bean
        this.position = position
        tv.text = bean
    }

}