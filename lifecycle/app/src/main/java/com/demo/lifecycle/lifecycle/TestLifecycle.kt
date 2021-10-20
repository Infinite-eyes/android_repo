package com.demo.lifecycle.lifecycle

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class TestLifecycle : LifecycleObserver{

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public fun aa(){
        Log.e("test"," @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public fun bb(){
        Log.e("test"," @OnLifecycleEvent(Lifecycle.Event.ON_START)");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public fun cc(){
        Log.e("test"," @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)");
    }

}

