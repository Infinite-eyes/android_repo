package com.demo.nav.statusbar.ui

import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.demo.nav.R
import com.demo.nav.StatusBarUtil
import com.demo.nav.databinding.ActivityColorStatusBarBinding
import java.util.*

open class ColorStatusBarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityColorStatusBarBinding

    private var mColor = 0
    private var mAlpha = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityColorStatusBarBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {

            if (supportActionBar != null) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            }

            // 改变颜色
            btnChangeColor.setOnClickListener {
                val random = Random()
                mColor = -0x1000000 or random.nextInt(0xffffff)
                StatusBarUtil.setColor(this@ColorStatusBarActivity, mColor, mAlpha)
            }

            sbChangeAlpha.max = 255
            sbChangeAlpha.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    mAlpha = progress
                    StatusBarUtil.setColor(this@ColorStatusBarActivity, mColor, mAlpha)
                    tvStatusAlpha.text = mAlpha.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
            sbChangeAlpha.progress = StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA

        }

        setStatusBar()
    }


    private fun setStatusBar() {
        mColor = resources.getColor(R.color.purple_200)
        StatusBarUtil.setColor(this, mColor)
    }
}