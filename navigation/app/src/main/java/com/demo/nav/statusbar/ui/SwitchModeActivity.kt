package com.demo.nav.statusbar.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.demo.nav.R
import com.demo.nav.StatusBarUtil

class SwitchModeActivity : AppCompatActivity() {

    private var mToolbar: Toolbar? = null
    private var mBtnSetLightMode: Button? = null
    private var mBtnSetDarkMode: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_mode)
        mToolbar = findViewById(R.id.toolbar)
        mBtnSetLightMode = findViewById(R.id.btn_set_light_mode)
        mBtnSetDarkMode = findViewById(R.id.btn_set_dark_mode)

        // 设置toolbar
//        setSupportActionBar(mToolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        mBtnSetLightMode?.setOnClickListener {
            val color = resources.getColor(R.color.light_gray)
            StatusBarUtil.setColor(this@SwitchModeActivity, color, 30)
            mToolbar?.setBackgroundColor(color)
            StatusBarUtil.setLightMode(this@SwitchModeActivity)
        }
        mBtnSetDarkMode?.setOnClickListener {
            val color = resources.getColor(R.color.colorPrimary)
            StatusBarUtil.setColor(this@SwitchModeActivity, color)
            mToolbar?.setBackgroundColor(color)
            StatusBarUtil.setDarkMode(this@SwitchModeActivity)
        }
        setStatusBar()
    }

    protected fun setStatusBar() {
        StatusBarUtil.setColor(this, resources.getColor(R.color.colorPrimary))
    }

}