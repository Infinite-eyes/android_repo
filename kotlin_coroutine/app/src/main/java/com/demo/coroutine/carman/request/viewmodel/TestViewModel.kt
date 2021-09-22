package com.demo.coroutine.carman.request.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.coroutine.carman.bean.User

class TestViewModel: ViewModel(){
    private val _user : MutableLiveData<User> = MutableLiveData(User(1,"测试"))
    val mUser: LiveData<User> = _user
}