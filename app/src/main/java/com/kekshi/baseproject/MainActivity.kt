package com.kekshi.baseproject

import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.gyf.barlibrary.ImmersionBar
import com.kekshi.baselib.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_layout.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setNavigationItemSelectedListener(this)

        toolbar.setNavigationIcon(R.drawable.ic_navigation_menu)
        toolbar.setNavigationOnClickListener { drawer.openDrawer(GravityCompat.START) }

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
}
