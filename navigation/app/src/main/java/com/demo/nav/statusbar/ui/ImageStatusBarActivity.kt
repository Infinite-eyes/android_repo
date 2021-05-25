package com.demo.nav.statusbar.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.demo.nav.StatusBarUtil
import com.demo.nav.databinding.ActivityImageStatusBarBinding

class ImageStatusBarActivity : AppCompatActivity() {

    companion object {
        val EXTRA_IS_TRANSPARENT = "is_transparent"
    }

    private lateinit var binding: ActivityImageStatusBarBinding

    private var isBgChanged = false
    private var isTransparent = false
    private var mAlpha = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageStatusBarBinding.inflate(layoutInflater)
        setContentView(binding.root)


        isTransparent = intent.getBooleanExtra(EXTRA_IS_TRANSPARENT, false)
        binding.apply {
            btnChangeBackground.setOnClickListener {
                isBgChanged = !isBgChanged
                if (isBgChanged) {
                    rootLayout.setBackgroundColor(Color.BLUE)
                } else {
                    rootLayout.setBackgroundColor(Color.YELLOW)
                }
            }

            if (!isTransparent) {
                sbChangeAlpha.visibility = View.VISIBLE
                sbChangeAlpha.max = 255
                sbChangeAlpha.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        mAlpha = progress;
                        StatusBarUtil.setTranslucent(this@ImageStatusBarActivity,mAlpha)
                        tvStatusAlpha.text = mAlpha.toString();
                    }
                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                })
            } else {
                sbChangeAlpha.visibility = View.GONE
            }
        }

        if (isTransparent) {
            StatusBarUtil.setTransparent(this)
        } else {
            StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA)
        }
    }
}