package com.demo.nav.statusbar.ui

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.demo.nav.R
import com.demo.nav.statusbar.ui.fragment.ImageFragment
import com.demo.nav.statusbar.ui.fragment.SimpleFragment
import java.util.*

class UseInFragmentActivity : AppCompatActivity() {
//
//    private var mVpHome: ViewPager? = null
//    private var mBottomNavigationBar: BottomNavigationBar? = null
//    private val mFragmentList: ArrayList<Fragment> = ArrayList<Fragment>()
//
//    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_use_in_fragment)
//        mVpHome = findViewById<ViewPager>(R.id.vp_home)
//        mBottomNavigationBar = findViewById(R.id.bottom_navigation_bar)
//        mBottomNavigationBar.addItem(BottomNavigationItem(R.drawable.ic_favorite, "One"))
//            .addItem(BottomNavigationItem(R.drawable.ic_gavel, "Two"))
//            .addItem(BottomNavigationItem(R.drawable.ic_grade, "Three"))
//            .addItem(BottomNavigationItem(R.drawable.ic_group_work, "Four"))
//            .initialise()
//        mBottomNavigationBar.setTabSelectedListener(object : OnTabSelectedListener() {
//            fun onTabSelected(position: Int) {
//                mVpHome.setCurrentItem(position)
//            }
//
//            fun onTabUnselected(position: Int) {}
//            fun onTabReselected(position: Int) {}
//        })
//        mFragmentList.add(ImageFragment())
//        mFragmentList.add(SimpleFragment())
//        mFragmentList.add(SimpleFragment())
//        mFragmentList.add(SimpleFragment())
//        mVpHome.addOnPageChangeListener(object : ViewPager.OnPageChangeListener() {
//            fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
//            fun onPageSelected(position: Int) {
//                mBottomNavigationBar.selectTab(position)
//                when (position) {
//                    0 -> {
//                    }
//                    else -> {
//                        val random = Random()
//                        val color = -0x1000000 or random.nextInt(0xffffff)
//                        if (mFragmentList[position] is SimpleFragment) {
//                            (mFragmentList[position] as SimpleFragment).setTvTitleBackgroundColor(
//                                color
//                            )
//                        }
//                    }
//                }
//            }
//
//            fun onPageScrollStateChanged(state: Int) {}
//        })
//        mVpHome.setAdapter(object : FragmentPagerAdapter(supportFragmentManager) {
//            fun getItem(position: Int): Fragment? {
//                return mFragmentList[position]
//            }
//
//            val count: Int
//                get() = mFragmentList.size
//        })
//    }
//
//    protected fun setStatusBar() {
//        StatusBarUtil.setTranslucentForImageViewInFragment(this@UseInFragmentActivity, null)
//    }
}