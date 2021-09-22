package com.demo.coroutine.carman.request.repository

import com.demo.coroutine.carman.constant.HttpConstant
import com.demo.coroutine.carman.remote.CResponse


open class BaseRepository {
    suspend fun <T:Any> handResponse(response: CResponse<T>, onSuccess:suspend ()-> Unit, errorBlock:suspend ()->Unit){
        when{
            response == null -> errorBlock()
            response.code == HttpConstant.OK -> onSuccess()
            else -> errorBlock()
        }
    }
}