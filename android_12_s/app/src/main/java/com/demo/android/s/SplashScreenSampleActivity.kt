package com.demo.android.s

import android.app.usage.UsageStatsManager
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen




/**
 *
 * https://juejin.cn/post/7014491424112508936
 */
class SplashScreenSampleActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        setContentView(R.layout.activity_main)


        // test OverScroll 滚动动画增加
        var str = ""
        repeat(55) {
            str += "Hello World!\n"
        }
        findViewById<TextView>(R.id.tv_content).text = str
        //        如果你不喜欢这个动画的话，你也可以通过 xml 中设置
        //        1.android:overScrollMode="never" 或者使用代码设置
        //        2.recyclerview.setOverScrollMode(View.OVER_SCROLL_NEVER); 来屏蔽默认的滚动动画。


//        引入限制域概念(官方翻译: 限制性应用待机模式存储分区)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            val manager = getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
            if(manager!=null) manager.appStandbyBucket
        }
//        adb shell am set-standby-bucket PACKAGE_NAME restricted



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            val  metrics = windowManager.currentWindowMetrics
            val width = metrics.bounds.width()
            val height = metrics.bounds.height()
        }

    }
}