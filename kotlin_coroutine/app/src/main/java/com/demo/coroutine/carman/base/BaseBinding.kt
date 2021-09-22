package com.demo.coroutine.carman.base

import androidx.databinding.ViewDataBinding

interface BaseBinding<VB: ViewDataBinding>{
    fun VB.initBinding()
}