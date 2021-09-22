package com.demo.coroutine.carman.interf

import android.view.View

object DoubleClickHelper {

    private const val windowDuration = 1

    @JvmOverloads
    fun click(view: View?, r:Runnable?, durationSeconds: Int = windowDuration){
        if(view == null || r== null){
            return
        }
    }

    @JvmOverloads
    fun click(view:View?, listener:View.OnClickListener?,durationSeconds: Int = windowDuration){
        if(view ==  null || listener == null){
            return
        }
    }
}