package com.demo.slidr.model

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange

class SlidrConfig private constructor() {
    /**
     * Get the primary color that the slider will interpolate. That is this color is the color
     * of the status bar of the Activity you are returning to
     *
     * @return      the primary status bar color
     */
    var primaryColor = -1
        private set

    /**
     * Get the secondary color that the slider will interpolatel That is the color of the Activity
     * that you are making slidable
     *
     * @return      the secondary status bar color
     */
    var secondaryColor = -1
        private set
    private var touchSize = -1f
    private var sensitivity = 1f
    private var scrimColor = Color.BLACK
    private var scrimStartAlpha = 0.8f
    private var scrimEndAlpha = 0f
    private var velocityThreshold = 5f
    private var distanceThreshold = 0.25f

    /**
     * Has the user configured slidr to only catch at the edge of the screen ?
     *
     * @return      true if is edge capture only
     */
    var isEdgeOnly = false
        private set
    private var edgeSize = 0.18f
    private var position: SlidrPosition = SlidrPosition.LEFT
    private var listener: SlidrListener? = null
    /***********************************************************************************************
     *
     * Getters
     *
     */
    /**
     * Get the color of the background scrim
     *
     * @return      the scrim color integer
     */
    @ColorInt
    fun getScrimColor(): Int {
        return scrimColor
    }

    /**
     * Get teh start alpha value for when the activity is not swiped at all
     *
     * @return      the start alpha value (0.0 to 1.0)
     */
    fun getScrimStartAlpha(): Float {
        return scrimStartAlpha
    }

    /**
     * Get the end alpha value for when the user almost swipes the activity off the screen
     *
     * @return      the end alpha value (0.0 to 1.0)
     */
    fun getScrimEndAlpha(): Float {
        return scrimEndAlpha
    }

    /**
     * Get the position of the slidable mechanism for this configuration. This is the position on
     * the screen that the user can swipe the activity away from
     *
     * @return      the slider position
     */
    fun getPosition(): SlidrPosition {
        return position
    }

    /**
     * Get the touch 'width' to be used in the gesture detection. This value should incorporate with
     * the device's touch slop
     *
     * @return      the touch area size
     */
    fun getTouchSize(): Float {
        return touchSize
    }

    /**
     * Get the velocity threshold at which the slide action is completed regardless of offset
     * distance of the drag
     *
     * @return      the velocity threshold
     */
    fun getVelocityThreshold(): Float {
        return velocityThreshold
    }

    /**
     * Get at what % of the screen is the minimum viable distance the activity has to be dragged
     * in-order to be slinged off the screen
     *
     * @return      the distant threshold as a percentage of the screen size (width or height)
     */
    fun getDistanceThreshold(): Float {
        return distanceThreshold
    }

    /**
     * Get the touch sensitivity set in the [ViewDragHelper] when
     * creating it.
     *
     * @return      the touch sensitivity
     */
    fun getSensitivity(): Float {
        return sensitivity
    }

    /**
     * Get the slidr listener set by the user to respond to certain events in the sliding
     * mechanism.
     *
     * @return      the slidr listener
     */
    fun getListener(): SlidrListener? {
        return listener
    }

    /**
     * Get the size of the edge field that is catchable
     *
     * @see .isEdgeOnly
     * @return      the size of the edge that is grabable
     */
    fun getEdgeSize(size: Float): Float {
        return edgeSize * size
    }


    class Builder {
        private val config: SlidrConfig
        fun primaryColor(@ColorInt color: Int): Builder {
            config.primaryColor = color
            return this
        }

        fun secondaryColor(@ColorInt color: Int): Builder {
            config.secondaryColor = color
            return this
        }

        fun position(position: SlidrPosition): Builder {
            config.position = position
            return this
        }

        fun touchSize(size: Float): Builder {
            config.touchSize = size
            return this
        }

        fun sensitivity(sensitivity: Float): Builder {
            config.sensitivity = sensitivity
            return this
        }

        fun scrimColor(@ColorInt color: Int): Builder {
            config.scrimColor = color
            return this
        }

        fun scrimStartAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float): Builder {
            config.scrimStartAlpha = alpha
            return this
        }

        fun scrimEndAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float): Builder {
            config.scrimEndAlpha = alpha
            return this
        }

        fun velocityThreshold(threshold: Float): Builder {
            config.velocityThreshold = threshold
            return this
        }

        fun distanceThreshold(@FloatRange(from = 0.1, to = 0.9) threshold: Float): Builder {
            config.distanceThreshold = threshold
            return this
        }

        fun edge(flag: Boolean): Builder {
            config.isEdgeOnly = flag
            return this
        }

        fun edgeSize(@FloatRange(from = 0.0, to = 1.0) edgeSize: Float): Builder {
            config.edgeSize = edgeSize
            return this
        }

        fun listener(listener: SlidrListener?): Builder {
            config.listener = listener
            return this
        }

        fun build(): SlidrConfig {
            return config
        }

        init {
            config = SlidrConfig()
        }
    }
}
