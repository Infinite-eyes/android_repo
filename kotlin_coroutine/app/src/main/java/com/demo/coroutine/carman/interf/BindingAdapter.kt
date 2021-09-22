package com.demo.coroutine.carman.interf

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("bindOnClick")
fun bindOnClick(view: View?, listener: View.OnClickListener?) {
    DoubleClickHelper.click(view, listener)
}