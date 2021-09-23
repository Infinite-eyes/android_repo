package com.demo.startup

import android.content.Context
import androidx.startup.Initializer

class LitePalInitializer : Initializer<Unit> {

//    我们去进行之前要进行的初始化操作就可以了，create()方法会把我们需要的Context参数传递进来。
    override fun create(context: Context) {
//       init
    }
//    当前的LitePalInitializer是否还依赖于其他的Initializer，如果有的话，就在这里进行配置，App Startup会保证先初始化依赖的Initializer，然后才会初始化当前的LitePalInitializer。
//    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
////        return listOf(OtherInitializer::class.java)
////        TODO("Not yet implemented")
//    }


    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }


}