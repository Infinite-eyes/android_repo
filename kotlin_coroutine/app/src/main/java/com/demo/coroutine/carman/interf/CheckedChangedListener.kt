package com.demo.coroutine.carman.interf

import android.widget.CompoundButton

interface CheckedChangedListener<T> {
    fun onCheckedChanged(buttonView: CompoundButton, isCHecked: Boolean, data: T)
}