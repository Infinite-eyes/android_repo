package com.demo.slidr

import android.app.Activity
import com.demo.slidr.model.SlidrConfig

internal class ConfigPanelSlideListener(activity: Activity, config: SlidrConfig) : ColorPanelSlideListener(activity, -1, -1) {
    private val config: SlidrConfig
    override fun onStateChanged(state: Int) {
        if (config.getListener() != null) {
            config.getListener()!!.onSlideStateChanged(state)
        }
    }

    override fun onClosed() {
        if (config.getListener() != null) {
            if (config.getListener()!!.onSlideClosed()) {
                return
            }
        }
        super.onClosed()
    }

    override fun onOpened() {
        if (config.getListener() != null) {
            config.getListener()!!.onSlideOpened()
        }
    }

    override fun onSlideChange(percent: Float) {
        super.onSlideChange(percent)
        if (config.getListener() != null) {
            config.getListener()!!.onSlideChange(percent)
        }
    }

    protected val primaryColor: Int
        protected get() = config.primaryColor
    protected val secondaryColor: Int
        protected get() = config.secondaryColor

    init {
        this.config = config
    }
}
