package com.demo.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.demo.nav.databinding.FragmentTabBinding
import com.demo.nav.ui.FirstFragment
import com.demo.nav.ui.SecondFragment

class TabFragment : Fragment() {

    private var _binding: FragmentTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            vp.isUserInputEnabled = false
            vp.offscreenPageLimit = 1
            vp.adapter = object : FragmentStateAdapter(this@TabFragment) {
                override fun getItemCount(): Int {
                    return 2
                }
                override fun createFragment(position: Int): Fragment {
                    when (position) {
                        0 -> {
                            return FirstFragment()
                        }
                        1 -> {
                            return SecondFragment()
                        }
                    }
                    return Fragment()
                }
            }

            super.onViewCreated(view, savedInstanceState)
        }
    }
}