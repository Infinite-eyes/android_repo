package com.demo.coroutine.carman.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.demo.coroutine.carman.extensions.getViewBinding

abstract class BaseVBActivity<VB: ViewDataBinding> :AppCompatActivity(),BaseBinding<VB> {

    protected val mBinding: VB by lazy(mode= LazyThreadSafetyMode.NONE){
        getViewBinding(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initObserve()
        mBinding.initBinding()
    }

    abstract fun initObserve()


}