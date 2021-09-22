package com.demo.coroutine.carman.request.repository

import com.demo.coroutine.carman.bean.Weather
import com.demo.coroutine.carman.remote.CResponse
import com.demo.coroutine.carman.remote.ServerApi
import javax.inject.Inject

class MainRepository  @Inject constructor():BaseRepository(){

  suspend fun getWeather(
      area:String
  ): CResponse<Weather> {
      return ServerApi.service.getWeather(area)
  }

}