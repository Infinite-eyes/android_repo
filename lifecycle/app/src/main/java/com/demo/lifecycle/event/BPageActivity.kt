package com.demo.lifecycle.event

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.demo.lifecycle.R
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class BPageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
        EventBus.getDefault().register(this@BPageActivity)
    }


    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    fun receive(msg: String) {
        Log.e("eventBus1", "show5: $msg");
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this@BPageActivity)
        //清空粘性事件的缓存
//        EventBus.getDefault().removeAllStickyEvents()
    }



}