package com.kekshi.baseproject

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.widget.DatePicker
import androidx.core.content.FileProvider
import com.kekshi.baselib.base.BaseActivity
import com.kekshi.baselib.utils.ImageUtils
import com.kekshi.baselib.utils.PermissionUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_test.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*


class TestActivity : BaseActivity() {

    var imageUri: Uri? = null
    var picFront: File? = null
    var picBack: File? = null
    lateinit var outputImage: File
    val REQUEST_CODE_PHOTO_UP = 1
    val REQUEST_CODE_PHOTO_DOWN = 2
    val REQUEST_CODE_PERMISSIONS = 3
    var currentState = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        btnDate.setOnClickListener {
            showDate()
        }
        //拍照并压缩显示
        btnPicture.setOnClickListener {
            toPicture(REQUEST_CODE_PHOTO_UP)
        }
        //拍照并压缩显示
        btnPicture2.setOnClickListener {
            toPicture(REQUEST_CODE_PHOTO_DOWN)
        }
    }

    private fun toPicture(requestCode: Int) {
        currentState = requestCode
        if (!PermissionUtils.isOverMarshmallow()) {
            getSystemPhone(currentState)
        } else {
            requestPermission()
        }
    }

    fun getSystemPhone(index: Int) {
        outputImage = File(
            Environment.getExternalStorageDirectory(),
            "id_$index.jpg"
        )
        try {
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(
                this,
                "com.kekshi.baseproject.fileProvider",
                outputImage
            )
        } else {
            imageUri = Uri.fromFile(outputImage)
        }
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, index)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != 0) {
            when (requestCode) {
                REQUEST_CODE_PHOTO_UP -> {
                    picFront = outputImage
                    setPhoneImage(imageUri!!)?.let { ivOne.setImageBitmap(it) }
                }
                REQUEST_CODE_PHOTO_DOWN -> {
                    picBack = outputImage
                    setPhoneImage(imageUri!!)?.let { ivTwo.setImageBitmap(it) }

                }
                REQUEST_CODE_PERMISSIONS -> requestPermission()
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun requestPermission() {
        RxPermissions(this)
            .requestEachCombined(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .subscribe { permission ->
                if (permission.granted) {
                    getSystemPhone(currentState)
                } else if (permission.shouldShowRequestPermissionRationale) {
                    showToast("权限未授权")
                } else {
                    toSettingPage()
                }
            }
    }

    private fun toSettingPage() {
        showAlertDialog(
            "请求权限",
            "权限被拒绝将导致某些功能无法正常使用"
        ) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivityForResult(intent, REQUEST_CODE_PERMISSIONS)
        }
    }

    fun setPhoneImage(uri: Uri): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            bitmap = ImageUtils.compressImage(uri)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return bitmap
    }

    private fun showDate() {
        val date = Calendar.getInstance()
        val myDate = Date()        // 创建一个Date实例
        date.time = myDate        // 设置日历的时间，把一个新建Date实例myDate传入
        val year = date.get(Calendar.YEAR)
        val month = date.get(Calendar.MONTH)
        val day = date.get(Calendar.DAY_OF_MONTH)
        //初始化默认日期year, month, day
        val datePickerDialog = DatePickerDialog(
            this,
            AlertDialog.THEME_HOLO_LIGHT,
//            android.R.style.Theme_Material_Light_Dialog_Alert,
            object : OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    btnDate.text = "${year}年-${month + 1}月-${dayOfMonth}日"
                }
            },
            year,
            month,
            day
        )
        datePickerDialog.setMessage("请选择日期")
        datePickerDialog.show()
    }
}
