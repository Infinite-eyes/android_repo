package com.demo.slidr

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.demo.slidr.model.SlidrConfig
import com.demo.slidr.widget.SliderPanel

class FragmentPanelSlideListener : SliderPanel.OnPanelSlideListener {

    private var view: View = view
    private var config: SlidrConfig? = null

    constructor(view: View,  config: SlidrConfig){
        this.config = config
    }

    override fun onStateChanged(state: Int) {
        if (config.getListener() != null) {
            config.getListener()!!.onSlideChange(state.toFloat())
        }
    }

    override fun onClosed() {
        if (config.getListener() != null) {
            if (config.getListener()!!.onSlideClosed()) {
                return;
            }
        }

        if (view.context is FragmentActivity) {
            val activity = view.context as FragmentActivity
            if (activity.supportFragmentManager.backStackEntryCount == 0) {
                activity.finish()
                activity.overridePendingTransition(0, 0)
            } else {
                activity.supportFragmentManager.popBackStack()
            }
        }
    }

    override fun onOpened() {
        if (config.getListener() != null) {
            config.getListener()!!.onSlideOpened()
        }
    }

    override fun onSlideChange(percent: Float) {
        if (config.getListener() != null) {
            config.getListener()!!.onSlideChange(percent)
        }
    }

}