package com.demo.flutter.entry

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.demo.flutter.entry.FlutterTools.destroyEngine


class MainActivity : AppCompatActivity(), View.OnClickListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button1).setOnClickListener(this)
        findViewById<Button>(R.id.button2).setOnClickListener(this)

        FlutterTools.preWarmFlutterEngine(this);
    }



    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button1 -> AndroidFlutterActivity.open(this, FlutterTools.ROUTE_PAGE_A)
            R.id.button2 -> AndroidFlutterActivity.open(this, FlutterTools.ROUTE_PAGE_B)
            else -> {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 释放资源
        destroyEngine()
    }
}