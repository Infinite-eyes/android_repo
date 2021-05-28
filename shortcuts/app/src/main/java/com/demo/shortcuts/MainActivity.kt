package com.demo.shortcuts

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.textview)
        when (intent.action) {
            ACTION_1 -> textView.text =
                ACTION_1
            ACTION_2 -> textView.text = ACTION_2
            ACTION_3 -> textView.text = ACTION_3
            else -> {
            }
        }
    }

    companion object {
        private const val ACTION_1 = "action1"
        private const val ACTION_2 = "action2"
        private const val ACTION_3 = "action3"
    }
}
