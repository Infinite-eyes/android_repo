package com.demo.coroutine.carman.ui

import android.util.Log
import androidx.activity.viewModels
import com.demo.coroutine.carman.base.BaseVBActivity
import com.demo.coroutine.carman.request.viewmodel.MainViewModel
import com.demo.coroutine.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

//https://juejin.cn/post/6986450257295081502
//@AndroidEntryPoint
class MainHiltActivity : BaseVBActivity<ActivityMainBinding>(){

    val viewMode: MainViewModel by viewModels()

    override fun initObserve() {
        viewMode.mUser.observe(this){
            Log.d("MainViewModel","user:$it")
        }
    }

    override fun ActivityMainBinding.initBinding() {
       this.mainViewModel= viewMode
    }


}