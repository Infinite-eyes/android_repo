package com.demo.hilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.demo.hilt.bean.Truck
import com.demo.hilt.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import javax.inject.Inject

//https://juejin.cn/post/6902009428633698312

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var truck: Truck

    @Inject
    lateinit var okHttpClient:OkHttpClient


//    v1
//    @Inject
//    lateinit var viewModel:MyViewModel

    val viewModel:MyViewModel by lazy {ViewModelProvider(this).get(MyViewModel::class.java)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        truck.deliver()
    }

}