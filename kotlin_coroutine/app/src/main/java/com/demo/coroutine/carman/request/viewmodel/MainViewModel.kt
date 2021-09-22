package com.demo.coroutine.carman.request.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.coroutine.R
import com.demo.coroutine.carman.bean.User
import com.demo.coroutine.carman.bean.Weather
import com.demo.coroutine.carman.extensions.requestMain
import com.demo.coroutine.carman.request.repository.MainRepository
import java.lang.Exception
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel(){
    private val _user: MutableLiveData<User> = MutableLiveData(User(1,"测试"))
    val mUser: LiveData<User> = _user

    private val _weather: MutableLiveData<Weather> = MutableLiveData()
    val mWeather: LiveData<Weather> = _weather

    fun getWeather(area:String){
        requestMain{
            val result = try{
                repository.getWeather(area)
            }catch (e: Exception){
                e.printStackTrace()
                null
            }
            _weather.postValue(result?.data)
        }
    }

    fun onClick(v: View){
        when(v.id){
            R.id.request_tbn->{
                getWeather("深圳")
            }
        }
    }



}