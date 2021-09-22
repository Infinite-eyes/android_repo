package com.demo.coroutine.carman.remote

import com.demo.coroutine.carman.bean.SysConfig
import com.demo.coroutine.carman.bean.Weather
import retrofit2.http.*

interface CoroutineService {


    @GET("/9-2")
    suspend fun getWeather(@Query("area") area:String): CResponse<Weather>

    @FormUrlEncoded
    @POST("/9-2")
    suspend fun postWeather(
        @Field("area") area: String
    ): CResponse<Weather>


    /**
     * 获取系统全局配置
     */
    @GET("sys/config")
    suspend fun getSysConfig(): CResponse<SysConfig>

    @FormUrlEncoded
    @POST("/user/push/reg")
    suspend fun registerPushId(
        @Field("plat") plat: String,
        @Field("identifier") identifier: String
    ): CResponse<Any>



}