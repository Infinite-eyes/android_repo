package com.demo.coroutine.carman.ui

import android.util.Log
import com.demo.coroutine.carman.base.BaseActivity
import com.demo.coroutine.carman.request.provideMainViewModelFactory
import com.demo.coroutine.carman.request.viewmodel.MainViewModel
import com.demo.coroutine.databinding.ActivityMainBinding
import androidx.activity.viewModels


class MainActivity :
    BaseActivity<ActivityMainBinding, MainViewModel>(provideMainViewModelFactory()) {


    val mainViewModel: MainViewModel by viewModels() {
        provideMainViewModelFactory()
    }

    override fun ActivityMainBinding.initBinding() {
        this.mainViewModel = viewModel
    }

    override fun initObserve() {
        viewModel.mUser.observe(this) {
            Log.d("MainViewModel", "user:$it")
        }
    }
}