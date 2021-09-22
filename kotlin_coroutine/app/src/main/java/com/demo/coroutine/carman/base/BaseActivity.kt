package com.demo.coroutine.carman.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.coroutine.carman.extensions.getViewBinding
import com.demo.coroutine.carman.request.ViewModelUtils

abstract class BaseActivity<VB : ViewDataBinding, VM : ViewModel>(
    private val factory: ViewModelProvider.Factory? = null
) :
    AppCompatActivity(), BaseBinding<VB> {

    protected val mBinding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        getViewBinding(layoutInflater)
    }
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        viewModel = ViewModelUtils.createViewModel(this, factory, 1)
        mBinding.initBinding()
        initObserve()
    }

    abstract fun initObserve()

    override fun onBackPressed() {
        val fragments: List<Fragment> = supportFragmentManager.fragments
        if (!fragments.isNullOrEmpty()) {
            for (fragment in fragments) {
                if(fragment is BaseFragment<*,*>){
                    if(fragment.onBackPressed()){
                        return
                    }
                }
            }
        }
        super.onBackPressed()
    }


}