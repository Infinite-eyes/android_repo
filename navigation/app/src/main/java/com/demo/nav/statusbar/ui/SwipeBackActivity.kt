package com.demo.nav.statusbar.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.demo.nav.R
import com.demo.nav.StatusBarUtil
import java.util.*

class SwipeBackActivity : AppCompatActivity() {


    private var mBtnChangeColor: Button? = null
    private var mColor = Color.GRAY
    private var mToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.swipe_back_activity)
        mToolbar = findViewById<Toolbar>(R.id.toolbar)
        mBtnChangeColor = findViewById<Button>(R.id.btn_change_color)

        setSupportActionBar(mToolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        mToolbar?.setBackgroundColor(mColor)
        mBtnChangeColor?.setOnClickListener {
            val random = Random()
            mColor = -0x1000000 or random.nextInt(0xffffff)
            mToolbar?.setBackgroundColor(mColor)
            StatusBarUtil.setColorForSwipeBack(this@SwipeBackActivity, mColor, 38)
        }
        StatusBarUtil.setColorForSwipeBack(this, mColor, 38)
    }



}