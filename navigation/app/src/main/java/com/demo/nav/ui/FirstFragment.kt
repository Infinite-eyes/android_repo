package com.demo.nav.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.demo.nav.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        immersionBar {
//            statusBarDarkFont(true)
//        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
//        activity?.window.setStatusBarColor(
////            ColorUtils.blendARGB(
////                mBarParams.statusBarColor,
////                mBarParams.statusBarColorTransform, mBarParams.statusBarAlpha
////            )
//        )
        super.onResume()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}