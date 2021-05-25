package com.demo.nav.statusbar

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.demo.nav.R
import com.demo.nav.StatusBarUtil
import com.demo.nav.databinding.ActivityStatusBarBinding
import com.demo.nav.statusbar.ui.*

class IndexActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatusBarBinding

    private var mStatusBarColor = 0
    private var mAlpha: Int = StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatusBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            btnSetColor.setOnClickListener {
                val intent = Intent(this@IndexActivity, ColorStatusBarActivity::class.java)
                startActivity(intent)
            }

            btnSetTransparent.setOnClickListener {
                val intent = Intent(this@IndexActivity, ImageStatusBarActivity::class.java)
                intent.putExtra(ImageStatusBarActivity.EXTRA_IS_TRANSPARENT, true)
                startActivity(intent)
            }

            btnSetTranslucent.setOnClickListener {
                val intent = Intent(this@IndexActivity, ImageStatusBarActivity::class.java)
                intent.putExtra(ImageStatusBarActivity.EXTRA_IS_TRANSPARENT, false)
                startActivity(intent)
            }

            btnSetForImageView.setOnClickListener {
                val intent = Intent(this@IndexActivity, ImageViewActivity::class.java)
                startActivity(intent)

            }
            btnUseInFragment.setOnClickListener {
                val intent = Intent(this@IndexActivity, UseInFragmentActivity::class.java)
                startActivity(intent)
            }

            btnSetColorForSwipeBack.setOnClickListener {
                val intent = Intent(this@IndexActivity, SwipeBackActivity::class.java)
                startActivity(intent)
            }

            btnSwitchMode.setOnClickListener {
                val intent = Intent(this@IndexActivity, SwitchModeActivity::class.java)
                startActivity(intent)
            }

            chbTranslucent.setOnClickListener {
                if (chbTranslucent.isChecked) {
                    main.setBackgroundColor(Color.parseColor("#FF00FF"))
                } else {
                    main.setBackgroundDrawable(null)
                }
            }
            sbChangeAlpha.max = 255
            sbChangeAlpha.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    mAlpha = progress;
                    tvStatusAlpha.setText(mAlpha.toString())
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }

            })
            sbChangeAlpha.progress = StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA
            mStatusBarColor = resources.getColor(R.color.purple_200)

        }


    }

}