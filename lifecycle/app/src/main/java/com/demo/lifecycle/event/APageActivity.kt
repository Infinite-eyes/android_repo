package com.demo.lifecycle.event

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.demo.lifecycle.R
import org.greenrobot.eventbus.EventBus

class APageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)



        findViewById<AppCompatButton>(R.id.acb_post).setOnClickListener {
            EventBus.getDefault().postSticky("AA")
        }
        findViewById<AppCompatButton>(R.id.acb_jump).setOnClickListener {
            startActivity(Intent(this,  BPageActivity::class.java))
        }

    }


}