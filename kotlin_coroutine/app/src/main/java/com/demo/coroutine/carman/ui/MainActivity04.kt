package com.demo.coroutine.carman.ui

import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.coroutine.carman.base.BaseVBActivity
import com.demo.coroutine.carman.bean.Student
import com.demo.coroutine.carman.bean.Teacher
import com.demo.coroutine.carman.interf.ItemClickListener
import com.demo.coroutine.carman.ui.adapter.HomeAdapter
import com.demo.coroutine.carman.ui.adapter.SecondAdapter
import com.demo.coroutine.databinding.ActivityMain04Binding

class MainActivity04: BaseVBActivity<ActivityMain04Binding>() {


    override fun initObserve() {
    }

    lateinit var homeAdapter: HomeAdapter

    override fun ActivityMain04Binding.initBinding() {

        val secondAdapter = SecondAdapter()

        with(recyclerView){
            layoutManager = LinearLayoutManager(this@MainActivity04).apply {
                orientation = RecyclerView.VERTICAL
            }
            adapter = secondAdapter
        }
        secondAdapter.setData(
            listOf(
                Teacher(1,"Person","语文"),
                Student(2,"Person","一年级"),
                Teacher(3,"Person","数学")
            )
        )
    }

    private val itemClickListener = object: ItemClickListener<String> {
        override fun onItemClick(view: View, data: String) {
            Log.d("onItemClick", "data:$data   position:${homeAdapter.getActualPosition(data)}")
        }
    }




}