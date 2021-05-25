package com.demo.slidr.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewGroupCompat
import com.demo.slidr.model.SlidrPosition
import com.demo.slidr.util.ViewDragHelper

class SliderPanel : FrameLayout {
    private var screenWidth = 0
    private var screenHeight = 0
    private var decorView: View? = null
    private var dragHelper: ViewDragHelper? = null
    private var listener: OnPanelSlideListener? = null
    private var scrimPaint: Paint? = null
    private var scrimRenderer: ScrimRenderer? = null
    private var isLocked = false
    private var isEdgeTouched = false
    private var edgePosition = 0
    private var config: SlidrConfig? = null

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, decorView: View?, config: SlidrConfig?) : super(
        context!!
    ) {
        this.decorView = decorView
        this.config = if (config == null) Builder().build() else config
        init()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val interceptForDrag: Boolean
        if (isLocked) {
            return false
        }
        if (config.isEdgeOnly()) {
            isEdgeTouched = canDragFromEdge(ev)
        }

        // Fix for pull request #13 and issue #12
        interceptForDrag = try {
            dragHelper.shouldInterceptTouchEvent(ev)
        } catch (e: Exception) {
            false
        }
        return interceptForDrag && !isLocked
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isLocked) {
            return false
        }
        try {
            dragHelper.processTouchEvent(event)
        } catch (e: IllegalArgumentException) {
            return false
        }
        return true
    }

    override fun computeScroll() {
        super.computeScroll()
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    override fun onDraw(canvas: Canvas) {
        scrimRenderer!!.render(canvas, config.getPosition(), scrimPaint!!)
    }

    /**
     * Set the panel slide listener that gets called based on slider changes
     * @param listener callback implementation
     */
    fun setOnPanelSlideListener(listener: OnPanelSlideListener?) {
        this.listener = listener
    }

    /**
     * Get the default [SlidrInterface] from which to control the panel with after attachment
     */
    val defaultInterface: SlidrInterface
        get() = defaultSlidrInterface
    private val defaultSlidrInterface: SlidrInterface = object : SlidrInterface() {
        fun lock() {
            this@SliderPanel.lock()
        }

        fun unlock() {
            this@SliderPanel.unlock()
        }
    }

    /**
     * The drag helper callback interface for the Left position
     */
    private val leftCallback: ViewDragHelper.Callback = object : Callback() {
        fun tryCaptureView(child: View, pointerId: Int): Boolean {
            val edgeCase = !config.isEdgeOnly() || dragHelper.isEdgeTouched(edgePosition, pointerId)
            return child.id == decorView!!.id && edgeCase
        }

        fun clampViewPositionHorizontal(child: View?, left: Int, dx: Int): Int {
            return clamp(left, 0, screenWidth)
        }

        fun getViewHorizontalDragRange(child: View?): Int {
            return screenWidth
        }

        fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            val left = releasedChild.left
            var settleLeft = 0
            val leftThreshold = (width * config.getDistanceThreshold()) as Int
            val isVerticalSwiping: Boolean = Math.abs(yvel) > config.getVelocityThreshold()
            if (xvel > 0) {
                if (Math.abs(xvel) > config.getVelocityThreshold() && !isVerticalSwiping) {
                    settleLeft = screenWidth
                } else if (left > leftThreshold) {
                    settleLeft = screenWidth
                }
            } else if (xvel == 0f) {
                if (left > leftThreshold) {
                    settleLeft = screenWidth
                }
            }
            dragHelper.settleCapturedViewAt(settleLeft, releasedChild.top)
            invalidate()
        }

        fun onViewPositionChanged(changedView: View?, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            val percent = 1f - left.toFloat() / screenWidth.toFloat()
            if (listener != null) listener!!.onSlideChange(percent)

            // Update the dimmer alpha
            applyScrim(percent)
        }

        fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
            if (listener != null) listener!!.onStateChanged(state)
            when (state) {
                ViewDragHelper.STATE_IDLE -> if (decorView!!.left == 0) {
                    // State Open
                    if (listener != null) listener!!.onOpened()
                } else {
                    // State Closed
                    if (listener != null) listener!!.onClosed()
                }
                ViewDragHelper.STATE_DRAGGING -> {
                }
                ViewDragHelper.STATE_SETTLING -> {
                }
            }
        }
    }

    /**
     * The drag helper callbacks for dragging the slidr attachment from the right of the screen
     */
    private val rightCallback: ViewDragHelper.Callback = object : Callback() {
        fun tryCaptureView(child: View, pointerId: Int): Boolean {
            val edgeCase = !config.isEdgeOnly() || dragHelper.isEdgeTouched(edgePosition, pointerId)
            return child.id == decorView!!.id && edgeCase
        }

        fun clampViewPositionHorizontal(child: View?, left: Int, dx: Int): Int {
            return clamp(left, -screenWidth, 0)
        }

        fun getViewHorizontalDragRange(child: View?): Int {
            return screenWidth
        }

        fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            val left = releasedChild.left
            var settleLeft = 0
            val leftThreshold = (width * config.getDistanceThreshold()) as Int
            val isVerticalSwiping: Boolean = Math.abs(yvel) > config.getVelocityThreshold()
            if (xvel < 0) {
                if (Math.abs(xvel) > config.getVelocityThreshold() && !isVerticalSwiping) {
                    settleLeft = -screenWidth
                } else if (left < -leftThreshold) {
                    settleLeft = -screenWidth
                }
            } else if (xvel == 0f) {
                if (left < -leftThreshold) {
                    settleLeft = -screenWidth
                }
            }
            dragHelper.settleCapturedViewAt(settleLeft, releasedChild.top)
            invalidate()
        }

        fun onViewPositionChanged(changedView: View?, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            val percent = 1f - Math.abs(left).toFloat() / screenWidth.toFloat()
            if (listener != null) listener!!.onSlideChange(percent)

            // Update the dimmer alpha
            applyScrim(percent)
        }

        fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
            if (listener != null) listener!!.onStateChanged(state)
            when (state) {
                ViewDragHelper.STATE_IDLE -> if (decorView!!.left == 0) {
                    // State Open
                    if (listener != null) listener!!.onOpened()
                } else {
                    // State Closed
                    if (listener != null) listener!!.onClosed()
                }
                ViewDragHelper.STATE_DRAGGING -> {
                }
                ViewDragHelper.STATE_SETTLING -> {
                }
            }
        }
    }

    /**
     * The drag helper callbacks for dragging the slidr attachment from the top of the screen
     */
    private val topCallback: ViewDragHelper.Callback = object : Callback() {
        fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child.id == decorView!!.id && (!config.isEdgeOnly() || isEdgeTouched)
        }

        fun clampViewPositionVertical(child: View?, top: Int, dy: Int): Int {
            return clamp(top, 0, screenHeight)
        }

        fun getViewVerticalDragRange(child: View?): Int {
            return screenHeight
        }

        fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            val top = releasedChild.top
            var settleTop = 0
            val topThreshold = (height * config.getDistanceThreshold()) as Int
            val isSideSwiping: Boolean = Math.abs(xvel) > config.getVelocityThreshold()
            if (yvel > 0) {
                if (Math.abs(yvel) > config.getVelocityThreshold() && !isSideSwiping) {
                    settleTop = screenHeight
                } else if (top > topThreshold) {
                    settleTop = screenHeight
                }
            } else if (yvel == 0f) {
                if (top > topThreshold) {
                    settleTop = screenHeight
                }
            }
            dragHelper.settleCapturedViewAt(releasedChild.left, settleTop)
            invalidate()
        }

        fun onViewPositionChanged(changedView: View?, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            val percent = 1f - Math.abs(top).toFloat() / screenHeight.toFloat()
            if (listener != null) listener!!.onSlideChange(percent)

            // Update the dimmer alpha
            applyScrim(percent)
        }

        fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
            if (listener != null) listener!!.onStateChanged(state)
            when (state) {
                ViewDragHelper.STATE_IDLE -> if (decorView!!.top == 0) {
                    // State Open
                    if (listener != null) listener!!.onOpened()
                } else {
                    // State Closed
                    if (listener != null) listener!!.onClosed()
                }
                ViewDragHelper.STATE_DRAGGING -> {
                }
                ViewDragHelper.STATE_SETTLING -> {
                }
            }
        }
    }

    /**
     * The drag helper callbacks for dragging the slidr attachment from the bottom of hte screen
     */
    private val bottomCallback: ViewDragHelper.Callback = object : Callback() {
        fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child.id == decorView!!.id && (!config.isEdgeOnly() || isEdgeTouched)
        }

        fun clampViewPositionVertical(child: View?, top: Int, dy: Int): Int {
            return clamp(top, -screenHeight, 0)
        }

        fun getViewVerticalDragRange(child: View?): Int {
            return screenHeight
        }

        fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            val top = releasedChild.top
            var settleTop = 0
            val topThreshold = (height * config.getDistanceThreshold()) as Int
            val isSideSwiping: Boolean = Math.abs(xvel) > config.getVelocityThreshold()
            if (yvel < 0) {
                if (Math.abs(yvel) > config.getVelocityThreshold() && !isSideSwiping) {
                    settleTop = -screenHeight
                } else if (top < -topThreshold) {
                    settleTop = -screenHeight
                }
            } else if (yvel == 0f) {
                if (top < -topThreshold) {
                    settleTop = -screenHeight
                }
            }
            dragHelper.settleCapturedViewAt(releasedChild.left, settleTop)
            invalidate()
        }

        fun onViewPositionChanged(changedView: View?, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            val percent = 1f - Math.abs(top).toFloat() / screenHeight.toFloat()
            if (listener != null) listener!!.onSlideChange(percent)

            // Update the dimmer alpha
            applyScrim(percent)
        }

        fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
            if (listener != null) listener!!.onStateChanged(state)
            when (state) {
                ViewDragHelper.STATE_IDLE -> if (decorView!!.top == 0) {
                    // State Open
                    if (listener != null) listener!!.onOpened()
                } else {
                    // State Closed
                    if (listener != null) listener!!.onClosed()
                }
                ViewDragHelper.STATE_DRAGGING -> {
                }
                ViewDragHelper.STATE_SETTLING -> {
                }
            }
        }
    }

    /**
     * The drag helper callbacks for dragging the slidr attachment in both vertical directions
     */
    private val verticalCallback: ViewDragHelper.Callback = object : Callback() {
        fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child.id == decorView!!.id && (!config.isEdgeOnly() || isEdgeTouched)
        }

        fun clampViewPositionVertical(child: View?, top: Int, dy: Int): Int {
            return clamp(top, -screenHeight, screenHeight)
        }

        fun getViewVerticalDragRange(child: View?): Int {
            return screenHeight
        }

        fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            val top = releasedChild.top
            var settleTop = 0
            val topThreshold = (height * config.getDistanceThreshold()) as Int
            val isSideSwiping: Boolean = Math.abs(xvel) > config.getVelocityThreshold()
            if (yvel > 0) {

                // Being slinged down
                if (Math.abs(yvel) > config.getVelocityThreshold() && !isSideSwiping) {
                    settleTop = screenHeight
                } else if (top > topThreshold) {
                    settleTop = screenHeight
                }
            } else if (yvel < 0) {
                // Being slinged up
                if (Math.abs(yvel) > config.getVelocityThreshold() && !isSideSwiping) {
                    settleTop = -screenHeight
                } else if (top < -topThreshold) {
                    settleTop = -screenHeight
                }
            } else {
                if (top > topThreshold) {
                    settleTop = screenHeight
                } else if (top < -topThreshold) {
                    settleTop = -screenHeight
                }
            }
            dragHelper.settleCapturedViewAt(releasedChild.left, settleTop)
            invalidate()
        }

        fun onViewPositionChanged(changedView: View?, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            val percent = 1f - Math.abs(top).toFloat() / screenHeight.toFloat()
            if (listener != null) listener!!.onSlideChange(percent)

            // Update the dimmer alpha
            applyScrim(percent)
        }

        fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
            if (listener != null) listener!!.onStateChanged(state)
            when (state) {
                ViewDragHelper.STATE_IDLE -> if (decorView!!.top == 0) {
                    // State Open
                    if (listener != null) listener!!.onOpened()
                } else {
                    // State Closed
                    if (listener != null) listener!!.onClosed()
                }
                ViewDragHelper.STATE_DRAGGING -> {
                }
                ViewDragHelper.STATE_SETTLING -> {
                }
            }
        }
    }

    /**
     * The drag helper callbacks for dragging the slidr attachment in both horizontal directions
     */
    private val horizontalCallback: ViewDragHelper.Callback = object : Callback() {
        fun tryCaptureView(child: View, pointerId: Int): Boolean {
            val edgeCase = !config.isEdgeOnly() || dragHelper.isEdgeTouched(edgePosition, pointerId)
            return child.id == decorView!!.id && edgeCase
        }

        fun clampViewPositionHorizontal(child: View?, left: Int, dx: Int): Int {
            return clamp(left, -screenWidth, screenWidth)
        }

        fun getViewHorizontalDragRange(child: View?): Int {
            return screenWidth
        }

        fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            val left = releasedChild.left
            var settleLeft = 0
            val leftThreshold = (width * config.getDistanceThreshold()) as Int
            val isVerticalSwiping: Boolean = Math.abs(yvel) > config.getVelocityThreshold()
            if (xvel > 0) {
                if (Math.abs(xvel) > config.getVelocityThreshold() && !isVerticalSwiping) {
                    settleLeft = screenWidth
                } else if (left > leftThreshold) {
                    settleLeft = screenWidth
                }
            } else if (xvel < 0) {
                if (Math.abs(xvel) > config.getVelocityThreshold() && !isVerticalSwiping) {
                    settleLeft = -screenWidth
                } else if (left < -leftThreshold) {
                    settleLeft = -screenWidth
                }
            } else {
                if (left > leftThreshold) {
                    settleLeft = screenWidth
                } else if (left < -leftThreshold) {
                    settleLeft = -screenWidth
                }
            }
            dragHelper.settleCapturedViewAt(settleLeft, releasedChild.top)
            invalidate()
        }

        fun onViewPositionChanged(changedView: View?, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            val percent = 1f - Math.abs(left).toFloat() / screenWidth.toFloat()
            if (listener != null) listener!!.onSlideChange(percent)

            // Update the dimmer alpha
            applyScrim(percent)
        }

        fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
            if (listener != null) listener!!.onStateChanged(state)
            when (state) {
                ViewDragHelper.STATE_IDLE -> if (decorView!!.left == 0) {
                    // State Open
                    if (listener != null) listener!!.onOpened()
                } else {
                    // State Closed
                    if (listener != null) listener!!.onClosed()
                }
                ViewDragHelper.STATE_DRAGGING -> {
                }
                ViewDragHelper.STATE_SETTLING -> {
                }
            }
        }
    }

    private fun init() {
        setWillNotDraw(false)
        screenWidth = resources.displayMetrics.widthPixels
        val density = resources.displayMetrics.density
        val minVel = MIN_FLING_VELOCITY * density
        val callback: ViewDragHelper.Callback
        when (config.getPosition()) {
            LEFT -> {
                callback = leftCallback
                edgePosition = ViewDragHelper.EDGE_LEFT
            }
            RIGHT -> {
                callback = rightCallback
                edgePosition = ViewDragHelper.EDGE_RIGHT
            }
            TOP -> {
                callback = topCallback
                edgePosition = ViewDragHelper.EDGE_TOP
            }
            BOTTOM -> {
                callback = bottomCallback
                edgePosition = ViewDragHelper.EDGE_BOTTOM
            }
            VERTICAL -> {
                callback = verticalCallback
                edgePosition = ViewDragHelper.EDGE_TOP or ViewDragHelper.EDGE_BOTTOM
            }
            HORIZONTAL -> {
                callback = horizontalCallback
                edgePosition = ViewDragHelper.EDGE_LEFT or ViewDragHelper.EDGE_RIGHT
            }
            else -> {
                callback = leftCallback
                edgePosition = ViewDragHelper.EDGE_LEFT
            }
        }


        dragHelper = ViewDragHelper.create(this, config.getSensitivity(), callback)
        dragHelper.setMinVelocity(minVel)
        dragHelper.setEdgeTrackingEnabled(edgePosition)
        ViewGroupCompat.setMotionEventSplittingEnabled(this, false)

        // Setup the dimmer view
        scrimPaint = Paint()
        scrimPaint!!.color = config.getScrimColor()
        scrimPaint!!.alpha = toAlpha(config.getScrimStartAlpha())
        scrimRenderer = ScrimRenderer(this, decorView!!)

        /*
         * This is so we can get the height of the view and
         * ignore the system navigation that would be included if we
         * retrieved this value from the DisplayMetrics
         */post { screenHeight = height }
    }

    private fun lock() {
        dragHelper.abort()
        isLocked = true
    }

    private fun unlock() {
        dragHelper.abort()
        isLocked = false
    }

    private fun canDragFromEdge(ev: MotionEvent): Boolean {
        val x = ev.x
        val y = ev.y
        when (config.getPosition()) {
            SlidrPosition.LEFT -> return x < config.getEdgeSize(width)
            SlidrPosition.RIGHT -> return x > width - config.getEdgeSize(width)
            SlidrPosition.BOTTOM -> return y > height - config.getEdgeSize(height)
            SlidrPosition.TOP -> return y < config.getEdgeSize(height)
            SlidrPosition.HORIZONTAL -> return x < config.getEdgeSize(width) || x > width - config.getEdgeSize(
                width
            )
            SlidrPosition.VERTICAL -> return y < config.getEdgeSize(height) || y > height - config.getEdgeSize(
                height
            )
        }
        return false
    }

    private fun applyScrim(percent: Float) {
        val alpha: Float =
            percent * (config.getScrimStartAlpha() - config.getScrimEndAlpha()) + config.getScrimEndAlpha()
        scrimPaint!!.alpha = toAlpha(alpha)
        invalidate(scrimRenderer!!.getDirtyRect(config.getPosition()))
    }

    /**
     * The panel sliding interface that gets called
     * whenever the panel is closed or opened
     */
    interface OnPanelSlideListener {
        fun onStateChanged(state: Int)
        fun onClosed()
        fun onOpened()
        fun onSlideChange(percent: Float)
    }

    companion object {
        private const val MIN_FLING_VELOCITY = 400 // dips per second
        private fun clamp(value: Int, min: Int, max: Int): Int {
            return Math.max(min, Math.min(max, value))
        }

        private fun toAlpha(percentage: Float): Int {
            return (percentage * 255).toInt()
        }
    }
}
