package com.demo.coroutine.carman.base

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.demo.coroutine.carman.exception.GlobalCoroutineExceptionHandler
import com.demo.coroutine.carman.extensions.NormalScope
import kotlinx.coroutines.*

abstract class BaseService : Service(){

    private val normalScope = NormalScope()

    override fun onDestroy() {
        normalScope.cancel()
        super.onDestroy()
    }


    protected fun requestMain(
        errCode: Int = -1, errMsg: String = "", report: Boolean = false,
        block: suspend CoroutineScope.() -> Unit) {
        normalScope.launch(GlobalCoroutineExceptionHandler(errCode, errMsg, report)) {
            block.invoke(this)
        }
    }


    protected fun requestIO(
        errCode: Int = -1, errMsg: String = "", report: Boolean = false,
        block: suspend CoroutineScope.() -> Unit): Job {
        return normalScope.launch(Dispatchers.IO + GlobalCoroutineExceptionHandler(errCode, errMsg, report)) {
            block.invoke(this)
        }
    }

    protected fun delayMain(
        delayTime: Long,errCode: Int = -1, errMsg: String = "", report: Boolean = false,
        block: suspend CoroutineScope.() -> Unit) {
        normalScope.launch(GlobalCoroutineExceptionHandler(errCode, errMsg, report)) {
            withContext(Dispatchers.IO) {
                delay(delayTime)
            }
            block.invoke(this)
        }
    }




}