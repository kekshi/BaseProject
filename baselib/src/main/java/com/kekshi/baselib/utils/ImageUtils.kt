package com.kekshi.baselib.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.ScrollView
import com.bumptech.glide.Glide
import com.kekshi.baselib.base.BaseApp.Companion.context
import java.io.*

object ImageUtils {
    fun showImg(context: Context, url: String, iv: ImageView) {
        Glide.with(context).load(url).into(iv)
    }

    /**
     * 截取scrollview的屏幕
     * @param ImageView
     * @return
     */
    fun getBitmapByView(imageView: ImageView): Bitmap {
        var bitmap: Bitmap? = null
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(
            imageView.width, imageView.height,
            Bitmap.Config.RGB_565
        )
        val canvas = Canvas(bitmap!!)
        imageView.draw(canvas)
        return bitmap
    }

    /**
     * 截取scrollview的屏幕
     * @param scrollView
     * @return
     */
    fun getBitmapByView(scrollView: ScrollView): Bitmap {
        var h = 0
        val bitmap: Bitmap?
        // 获取scrollview实际高度
        for (i in 0 until scrollView.childCount) {
            h += scrollView.getChildAt(i).height
            scrollView.getChildAt(i)
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(
            scrollView.width, h,
            Bitmap.Config.RGB_565
        )
        val canvas = Canvas(bitmap!!)
        scrollView.draw(canvas)
        return bitmap
    }

    /**
     * 压缩图片
     * @param fileName 压缩后名称
     * @param file 压缩文件
     * @return File
     */
    fun compress(fileName: String, file: File): File? {
        val bm = BitmapFactory.decodeFile(file.path)
        var outputImage = File(
            Environment.getExternalStorageDirectory(), "$fileName.jpg"
        )
        try {
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val bos = BufferedOutputStream(FileOutputStream(outputImage))
        bm.compress(Bitmap.CompressFormat.JPEG, 30, bos)
        bos.flush()
        bos.close()
        return outputImage
    }

    /**
     * 压缩图片
     * @param image
     * @return
     */
    fun compressImage(uri: Uri): Bitmap? {
//        BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri)).apply {
//            Log.d("ddddd", "原图大小是：${byteCount}")
//        }
        var input = context.contentResolver.openInputStream(uri)
        //先进行采样率压缩
        //这一段代码是不加载文件到内存中也得到bitmap的真是宽高，主要是设置inJustDecodeBounds为true
        val onlyBoundsOptions = BitmapFactory.Options()
        onlyBoundsOptions.inJustDecodeBounds = true//不加载到内存
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions)
        input?.close()

        val originalWidth = onlyBoundsOptions.outWidth
        val originalHeight = onlyBoundsOptions.outHeight
        if (originalWidth == -1 || originalHeight == -1)
            return null

        //图片分辨率以480x800为标准
        val height = 800//这里设置高度为800f
        val width = 480//这里设置宽度为480f
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inSampleSize = calculateInSampleSize(onlyBoundsOptions, width, height)
        bitmapOptions.inJustDecodeBounds = false
        input = context.contentResolver.openInputStream(uri)
        val image = BitmapFactory.decodeStream(input, null, bitmapOptions)
        Log.d("ddddd", "采样率压缩图大小是：${image.byteCount}")
        input?.close()

        // 再进行质量压缩，这里100表示不压缩，把压缩后的数据存放到baos中
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        var options = 100
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().size / 1024 > 1024) {
            // 重置baos
            baos.reset()
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos)
            // 每次都减少10
            options -= 10
            if (options <= 0)
                break
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        val isBm = ByteArrayInputStream(baos.toByteArray())
        // 把ByteArrayInputStream数据生成图片
        return BitmapFactory.decodeStream(isBm, null, null).apply {
            Log.d("ddddd", "质量压缩图大小是：${byteCount}")
        }
    }

    /**
     * 压缩图片
     * @param image
     * @return
     */
    fun compressImage(input: InputStream?): Bitmap? {
        //先进行采样率压缩
        //这一段代码是不加载文件到内存中也得到bitmap的真是宽高，主要是设置inJustDecodeBounds为true
        val onlyBoundsOptions = BitmapFactory.Options()
        onlyBoundsOptions.inJustDecodeBounds = true//不加载到内存
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions)

        val originalWidth = onlyBoundsOptions.outWidth
        val originalHeight = onlyBoundsOptions.outHeight
        if (originalWidth == -1 || originalHeight == -1)
            return null

        //图片分辨率以480x800为标准
        val height = 800//这里设置高度为800f
        val width = 480//这里设置宽度为480f
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inSampleSize = calculateInSampleSize(onlyBoundsOptions, width, height)
        bitmapOptions.inJustDecodeBounds = false
        val image = BitmapFactory.decodeStream(input, null, bitmapOptions)
        input?.close()

        // 再进行质量压缩，这里100表示不压缩，把压缩后的数据存放到baos中
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        var options = 100
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().size / 1024 > 1024) {
            // 重置baos
            baos.reset()
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos)
            // 每次都减少10
            options -= 10
            if (options <= 0)
                break
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        val isBm = ByteArrayInputStream(baos.toByteArray())
        // 把ByteArrayInputStream数据生成图片
        return BitmapFactory.decodeStream(isBm, null, null)
    }

    /**
     * 获取采样率
     * @param options
     * @param reqWidth 目标view的宽
     * @param reqHeight 目标view的高
     * @return 采样率
     */
    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {

        val originalWidth = options.outWidth
        val originalHeight = options.outHeight
        var inSampleSize = 1

        if (originalHeight > reqHeight || originalWidth > reqHeight) {
            val halfHeight = originalHeight / 2
            val halfWidth = originalWidth / 2
            //压缩后的尺寸与所需的尺寸进行比较
            while (halfWidth / inSampleSize >= reqHeight && halfHeight / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    /**
     * 保存到sdcard
     * @param b
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun savePic(b: Bitmap): Boolean {
        var isSave: Boolean
        try {
            MediaStore.Images.Media.insertImage(
                context.contentResolver,
                b, "image", null
            )
            isSave = true
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            isSave = false
        }
        return isSave
    }

    /**
     * 读取照片exif信息中的旋转角度
     *
     * @param path
     * 照片路径
     * @return角度
     */

    fun readPictureDegree(path: String): Int {
        var degree = 0
        try {
            val exifInterface = ExifInterface(path)
            val orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return degree
    }

    /**
     * 图片旋转
     *
     * @param tmpBitmap
     * @param degrees
     * @return
     */

    fun rotateToDegrees(tmpBitmap: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix()
        matrix.reset()
        matrix.setRotate(degrees)
        return Bitmap.createBitmap(tmpBitmap, 0, 0, tmpBitmap.width, tmpBitmap.height, matrix, true)
    }
}