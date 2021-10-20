package com.demo.lifecycle.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.lifecycle.R

class LifecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)
    }


    fun onClick(view: android.view.View) {
        lifecycle.addObserver(TestLifecycle())
    }
}