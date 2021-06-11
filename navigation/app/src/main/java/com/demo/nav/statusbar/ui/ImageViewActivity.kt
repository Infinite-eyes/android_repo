package com.demo.nav.statusbar.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.demo.nav.StatusBarUtil
import com.demo.nav.databinding.ActivityImageViewBinding
import com.r0adkll.slidr.Slidr

class ImageViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageViewBinding
    private var mAlpha = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImageViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        Slidr.attach(this)

        binding.apply {
//            setSupportActionBar(toolbar)
            if (supportActionBar != null) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            }
            sbChangeAlpha.max = 255;
            sbChangeAlpha.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    mAlpha = progress
                    StatusBarUtil.setTranslucentForImageView(
                        this@ImageViewActivity,
                        mAlpha,
                        viewNeedOffset
                    )
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            })
            sbChangeAlpha.progress = StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA

            StatusBarUtil.setTranslucentForImageView(this@ImageViewActivity, viewNeedOffset)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }


}