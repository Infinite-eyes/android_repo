package com.demo.lifecycle.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {
    val msg = MutableLiveData<Int>(1); //初始化值
    var count = 1
    fun getRemote() {
        //假设这里耗时，然后用post切换下线程
        count++
        msg.postValue(count)
//        msg.value = count
//        这边 postValue 和 setValue 同步时机不同
    //    因为postValue 是通过 handler 会有个延迟
    }
}