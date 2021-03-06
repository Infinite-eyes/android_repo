package com.demo.slidr

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import com.demo.slidr.model.SlidrConfig
import com.demo.slidr.model.SlidrInterface
import com.demo.slidr.widget.SliderPanel

class Slidr {

    fun attach(activity: Activity): SlidrInterface {
        return attach(activity, -1, -1)
    }


    fun attach(
        activity: Activity, @ColorInt statusBarColor1: Int,
        @ColorInt statusBarColor2: Int
    ): SlidrInterface {

        // Setup the slider panel and attach it to the decor
        val panel: SliderPanel = attachSliderPanel(activity, null)

        // Set the panel slide listener for when it becomes closed or opened
        panel.setOnPanelSlideListener(
            ColorPanelSlideListener(
                activity,
                statusBarColor1,
                statusBarColor2
            )
        )

        // Return the lock interface
        return panel.defaultInterface
    }


    /**
     * Attach a slider mechanism to an activity based on the passed [com.r0adkll.slidr.model.SlidrConfig]
     *
     * @param activity the activity to attach the slider to
     * @param config   the slider configuration to make
     * @return a [com.r0adkll.slidr.model.SlidrInterface] that allows
     * the user to lock/unlock the sliding mechanism for whatever purpose.
     */
    fun attach(activity: Activity, config: SlidrConfig): SlidrInterface {

        // Setup the slider panel and attach it to the decor
        val panel: SliderPanel = attachSliderPanel(activity, config)

        // Set the panel slide listener for when it becomes closed or opened
        panel.setOnPanelSlideListener(ConfigPanelSlideListener(activity, config))

        // Return the lock interface
        return panel.defaultInterface
    }


    /**
     * Attach a new [SliderPanel] to the root of the activity's content
     */
    private fun attachSliderPanel(activity: Activity, config: SlidrConfig): SliderPanel {
        // Hijack the decorview
        val decorView = activity.window.decorView as ViewGroup
        val oldScreen = decorView.getChildAt(0)
        decorView.removeViewAt(0)

        // Setup the slider panel and attach it to the decor
        val panel = SliderPanel(activity, oldScreen, config)
        panel.setId(R.id.slidable_panel)
        oldScreen.id = R.id.slidable_content
        panel.addView(oldScreen)
        decorView.addView(panel, 0)
        return panel
    }


    /**
     * Attach a slider mechanism to a fragment view replacing an internal view
     *
     * @param oldScreen the view within a fragment to replace
     * @param config the slider configuration to attach with
     * @return a [com.r0adkll.slidr.model.SlidrInterface] that allows
     * the user to lock/unlock the sliding mechanism for whatever purpose.
     */
    fun replace(oldScreen: View, config: SlidrConfig): SlidrInterface {
        val parent = oldScreen.parent as ViewGroup
        val params = oldScreen.layoutParams
        parent.removeView(oldScreen)

        // Setup the slider panel and attach it
        val panel = SliderPanel(oldScreen.context, oldScreen, config)
        panel.setId(R.id.slidable_panel)
        oldScreen.id = R.id.slidable_content
        panel.addView(oldScreen)
        parent.addView(panel, 0, params)

        // Set the panel slide listener for when it becomes closed or opened
        panel.setOnPanelSlideListener(FragmentPanelSlideListener(oldScreen, config))

        // Return the lock interface
        return panel.defaultInterface
    }


}