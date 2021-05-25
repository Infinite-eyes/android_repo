package com.demo.slidr.model

import androidx.customview.widget.ViewDragHelper

interface SlidrListener {
    /**
     * This is called when the [ViewDragHelper] calls it's
     * state change callback.
     *
     * @see ViewDragHelper.STATE_IDLE
     *
     * @see ViewDragHelper.STATE_DRAGGING
     *
     * @see ViewDragHelper.STATE_SETTLING
     *
     *
     * @param state     the [ViewDragHelper] state
     */
    fun onSlideStateChanged(state: Int)
    fun onSlideChange(percent: Float)
    fun onSlideOpened()

    /**
     * @return `true` than event was processed in the callback.
     */
    fun onSlideClosed(): Boolean
}
