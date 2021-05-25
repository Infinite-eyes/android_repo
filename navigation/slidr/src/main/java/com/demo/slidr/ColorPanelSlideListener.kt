package com.demo.slidr

import android.animation.ArgbEvaluator
import android.app.Activity
import android.os.Build
import androidx.annotation.ColorInt
import com.demo.slidr.widget.SliderPanel

internal class ColorPanelSlideListener(private val activity: Activity, @param:ColorInt protected val primaryColor: Int, @param:ColorInt protected val secondaryColor: Int) : SliderPanel.OnPanelSlideListener {

    private val evaluator = ArgbEvaluator()

    override fun onStateChanged(state: Int) {
        // Unused.
    }

    override fun onClosed() {
        activity.finish()
        activity.overridePendingTransition(0, 0)
    }

    override fun onOpened() {
        // Unused.
    }

    override fun onSlideChange(percent: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && areColorsValid()) {
            val newColor = evaluator.evaluate(percent, primaryColor, secondaryColor) as Int
            activity.window.statusBarColor = newColor
        }
    }

    protected fun areColorsValid(): Boolean {
        return primaryColor != -1 && secondaryColor != -1
    }
}
