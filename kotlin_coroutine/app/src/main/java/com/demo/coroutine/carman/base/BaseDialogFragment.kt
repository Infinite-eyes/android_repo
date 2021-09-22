package com.demo.coroutine.carman.base

import android.os.Bundle
import android.view.*
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.demo.coroutine.carman.extensions.getViewBinding

abstract class BaseDialogFragment<VB:ViewDataBinding> :DialogFragment(),BaseBinding<VB> {

    protected lateinit var mBinding:VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = getViewBinding(inflater,container)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)
        mBinding.initBinding()
    }


    override fun onResume() {
        dialog?.let {
            val params: ViewGroup.LayoutParams? =  it.window?.attributes
            params?.run{
                width = WindowManager.LayoutParams.MATCH_PARENT
                height = WindowManager.LayoutParams.MATCH_PARENT
                it.window?.attributes = this as WindowManager.LayoutParams
            }
        }
        super.onResume()
    }
}