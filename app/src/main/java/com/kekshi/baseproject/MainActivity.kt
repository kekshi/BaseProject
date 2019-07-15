package com.kekshi.baseproject

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.gyf.barlibrary.ImmersionBar
import com.kekshi.baselib.base.BaseActivity
import com.kekshi.baselib.base.BaseApp
import com.kekshi.baselib.utils.FileUtils
import com.kekshi.baselib.utils.MD5Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_layout.*
import java.io.File
import java.util.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setNavigationItemSelectedListener(this)
        refreshPermissionStatus()
        toolbar.setNavigationIcon(R.drawable.ic_navigation_menu)
        toolbar.setNavigationOnClickListener { drawer.openDrawer(GravityCompat.START) }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.xm1 -> showToast(item.title.toString())
            R.id.xm2 -> showToast(item.title.toString())
            R.id.xm3 -> {
                showToast(item.title.toString())
                val fileStr = "${FileUtils.getRootPath().absoluteFile}${File.separator}${FileUtils.DEVICES_FILE_NAME}"
                var file = File(fileStr)
                if (!file.exists()) {
                    file.createNewFile()
                    Log.e("MainActivitiKt", "file:" + file.absolutePath)
                    FileUtils.saveFileUTF8(file.absolutePath, "123456")
                }
                val utF8 = FileUtils.getFileUTF8(fileStr)
                Log.e("MainActivitiKt", "isSDCardEnable:" + FileUtils.isSDCardEnable())
                Log.e("MainActivitiKt", "sdCardIsAvailable:" + FileUtils.sdCardIsAvailable())
                Log.e("MainActivitiKt", "getRootPath:" + FileUtils.getRootPath().absolutePath)
                Log.e("MainActivitiKt", "getFileUTF8:" + utF8)
                val result = UUID.randomUUID().toString().replace("-", "") + android.os.Build.SERIAL
                Log.e("MainActivitiKt", "result:" + result)
                val md5 = MD5Utils.md5(result)
                Log.e("MainActivitiKt", "md5:" + md5)
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
        handlePermissions(
            arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), object : PermissionListener {
                override fun onGranted() {
                    //权限同意后的逻辑
                }

                @RequiresApi(Build.VERSION_CODES.M)
                override fun onDenied(deniedPermissions: List<String>) {
                    //权限拒绝后的逻辑
                    showAlertDialog("请求权限", "权限被拒绝将导致某些功能无法正常使用", {
                        hintPermissions(deniedPermissions)
                    })
                }
            })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun hintPermissions(deniedPermissions: List<String>) {
        var allNeverAskAgain = true
        for (deniedPermission in deniedPermissions) {
            //权限都被拒绝，且被标记为不再提示
            if (shouldShowRequestPermissionRationale(deniedPermission)) {
                allNeverAskAgain = false
                break
            }
        }
        if (allNeverAskAgain) {
            showToast("权限被拒绝了,请打开应用权限")
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", BaseApp.context.packageName, null)
            intent.data = uri
            startActivityForResult(intent, 1)
        } else {
            refreshPermissionStatus()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            1 -> refreshPermissionStatus()
            else -> {
            }
        }
    }
}
