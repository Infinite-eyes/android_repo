package com.demo.nav.statusbar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.demo.nav.R

class SimpleFragment : Fragment() {
    private var mTvTitle: TextView? = null
    private var mFakeStatusBar: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragement_simple, container, false)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTvTitle = view.findViewById<View>(R.id.tv_title) as TextView
        mFakeStatusBar = view.findViewById(R.id.fake_status_bar)
    }

    fun setTvTitleBackgroundColor(@ColorInt color: Int) {
        mTvTitle!!.setBackgroundColor(color)
        mFakeStatusBar!!.setBackgroundColor(color)
    }
}
