package com.demo.lifecycle.livedata

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.demo.lifecycle.R

class LiveDataActivity : AppCompatActivity() {

    var viewModel: TestViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(TestViewModel::class.java)
        viewModel?.getRemote()  //先生产个数据
        viewModel?.msg?.observe(this) {
            Log.e("test", "观察者--》${it}")
        }
        Log.e("test", "onCreate");
    }


//    ReportFragment--->dispatch(activity,event)
//    LifecycleRegistry--->observer.dispatchEvent(lifecycleOwner, event);
//    LiveData.LifecycleBoundObserver--->onStateChanged(source,event)

    override fun onStart() {
        super.onStart()
        Thread.sleep(4000)  //onStart耗时
    }
    override fun onResume() {
        super.onResume()
        Log.e("test", "onResume");
    }

    fun onClick(view:android.view.View){
        viewModel?.getRemote();
        viewModel?.msg?.observe(this){
            Log.e("test","第二个观察者--》${it}")
        }
    }





}