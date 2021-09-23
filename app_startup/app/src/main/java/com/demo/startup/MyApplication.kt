package com.demo.startup

import android.app.Application
import androidx.startup.AppInitializer

class MyApplication: Application() {


    override fun onCreate() {
        super.onCreate()
//        延迟初始化手动调用
//        AppInitializer.getInstance(this)
//            .initializeComponent(LitePalInitializer::class.java)
    }

}