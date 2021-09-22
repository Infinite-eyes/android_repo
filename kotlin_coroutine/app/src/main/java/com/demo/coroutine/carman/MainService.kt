package com.demo.coroutine.carman

import android.content.Intent
import android.os.IBinder
import com.demo.coroutine.carman.base.BaseService

class MainService: BaseService() {



    override fun onBind(intent: Intent?): IBinder?  = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        requestIO {

        }
        return super.onStartCommand(intent, flags, startId)
    }


}