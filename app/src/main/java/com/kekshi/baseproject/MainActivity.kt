package com.kekshi.baseproject

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.gyf.barlibrary.ImmersionBar
import com.kekshi.baselib.base.BaseActivity
import com.kekshi.baselib.base.BaseApp
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

    private fun refreshPermissionStatus() {
        handlePermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), object : PermissionListener {
            override fun onGranted() {
                //权限同意后的逻辑
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onDenied(deniedPermissions: List<String>) {
                //权限拒绝后的逻辑


                refreshPermissionStatus()

                var allNeverAskAgain = true
                for (deniedPermission in deniedPermissions) {
                    //权限都被拒绝，且被标记为不再提示
                    if (shouldShowRequestPermissionRationale(deniedPermission)) {
                        allNeverAskAgain = false
                        break
                    }
                }
                if (allNeverAskAgain) {
                    showToast(R.string.authority_setting_refuse)
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", BaseApp.context.packageName, null)
                    intent.data = uri
                    startActivityForResult(intent, 1)
                }
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            1 -> refreshPermissionStatus()
            else -> {
            }
        }
    }
}
