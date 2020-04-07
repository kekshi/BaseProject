package com.kekshi.baseproject

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.gyf.barlibrary.ImmersionBar
import com.kekshi.baselib.base.BaseActivity
import com.kekshi.baselib.permission.PermissionX
import com.kekshi.baseproject.fragment.FourFragment
import com.kekshi.baseproject.fragment.OneFragment
import com.kekshi.baseproject.fragment.ThreeFragment
import com.kekshi.baseproject.fragment.TwoFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_layout.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
    ViewPager.OnPageChangeListener {

    lateinit var fragmentList: ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setNavigationItemSelectedListener(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionX.request(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) { allGranted, deniedList ->
                if (allGranted) {
                    //有权限
                } else {
                }
            }
        }

        fragmentList = arrayListOf()
        fragmentList.add(OneFragment())
        fragmentList.add(TwoFragment())
        fragmentList.add(ThreeFragment())
        fragmentList.add(FourFragment())
        mViewPager.adapter = PageAdapter(supportFragmentManager, fragmentList)
        mViewPager.addOnPageChangeListener(this)

        // 不使用图标默认变色
        mBottomNavigationView.itemIconTintList = null
        mBottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    //不使用滑动动画
                    mViewPager.setCurrentItem(0, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.home_found -> {
                    mViewPager.setCurrentItem(1, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.home_message -> {
                    mViewPager.setCurrentItem(2, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.home_me -> {
                    mViewPager.setCurrentItem(3, false)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.xm1 -> showToast(item.title.toString())
            R.id.xm2 -> showToast(item.title.toString())
            R.id.xm3 -> {
                showToast(item.title.toString())
            }
            R.id.dez1 -> {
                showToast(item.title.toString())
            }
            R.id.dez2 -> showToast(item.title.toString())
            else -> return false
        }
        item.isChecked = true
        drawer.closeDrawers()
        return true
    }


    override fun onDestroy() {
        super.onDestroy()
        ImmersionBar.with(this).destroy()
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                mBottomNavigationView.selectedItemId = R.id.menu_home
            }
            1 -> {
                mBottomNavigationView.selectedItemId = R.id.home_found
            }
            2 -> {
                mBottomNavigationView.selectedItemId = R.id.home_message
            }
            3 -> {
                mBottomNavigationView.selectedItemId = R.id.home_me
            }
        }

    }
}
