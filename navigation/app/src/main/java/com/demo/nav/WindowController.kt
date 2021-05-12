package com.demo.nav

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController


fun Window.setDarkStatus(isDark: Boolean) {
    if (isDark) {
        setDarkStatusBar()
    } else {
        setLightStatusBar()
    }
}

fun Window.setDarkStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        insetsController?.setSystemBarsAppearance(
            0,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        decorView.let {
            it.systemUiVisibility =
                it.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }
}

fun Window.setLightStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        decorView.let {
            it.systemUiVisibility = it.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}