package com.kekshi.baselib.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.ScrollView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.kekshi.baselib.R
import com.kekshi.baselib.base.BaseApp.Companion.context
import java.io.*

object ImageUtils {
    fun showImg(context: Context, url: String, iv: ImageView) {
        Glide.with(context).load(url).into(iv)
    }

    fun showImg(context: Context, url: Uri, iv: ImageView) {
        Glide.with(context).load(url).skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE).into(iv)
    }

    fun showImg(context: Context, url: Uri): Drawable {
        return Glide.with(context).load(url).skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE).submit().get()
    }

    //加载指定大小
    fun loadImageViewSize(
        mContext: Context?,
        path: String?,
        width: Int,
        height: Int,
        mImageView: ImageView?
    ) {
        Glide.with(mContext!!).load(path).override(width, height).into(mImageView!!)
    }

    //设置加载中以及加载失败图片
    fun loadImageViewLoding(
        mContext: Context?,
        path: String?,
        mImageView: ImageView?,
        lodingImage: Int,
        errorImageView: Int
    ) {
        Glide.with(mContext!!).load(path).placeholder(lodingImage).error(errorImageView)
            .into(mImageView!!)
    }

    //设置加载中以及加载失败图片并且指定大小
    fun loadImageViewLodingSize(
        mContext: Context?,
        path: String?,
        width: Int,
        height: Int,
        mImageView: ImageView?,
        lodingImage: Int,
        errorImageView: Int
    ) {
        Glide.with(mContext!!).load(path).override(width, height).placeholder(lodingImage)
            .error(errorImageView).into(mImageView!!)
    }

    //设置跳过内存缓存
    fun loadImageViewCache(
        mContext: Context?,
        path: String?,
        mImageView: ImageView?
    ) {
        Glide.with(mContext!!).load(path).skipMemoryCache(true).into(mImageView!!)
    }

    //设置下载优先级
    fun loadImageViewPriority(
        mContext: Context?,
        path: String?,
        mImageView: ImageView?
    ) {
        Glide.with(mContext!!).load(path).priority(Priority.NORMAL)
            .into(mImageView!!)
    }

    /**
     * 策略解说：
     *
     * all:缓存源资源和转换后的资源
     * none:不作任何磁盘缓存
     * source:缓存源资源
     * result：缓存转换后的资源
     */
    //设置缓存策略
    fun loadImageViewDiskCache(
        mContext: Context?,
        path: String?,
        mImageView: ImageView?
    ) {
        Glide.with(mContext!!).load(path).diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(mImageView!!)
    }

    /**
     * 会先加载缩略图
     */
    //设置缩略图支持
    fun loadImageViewThumbnail(
        mContext: Context?,
        path: String?,
        mImageView: ImageView?
    ) {
        Glide.with(mContext!!).load(path).thumbnail(0.1f).into(mImageView!!)
    }

    /**
     * 加载圆角图片
     *
     * @param context 上下文
     * @param view    图片控件
     * @param url     网络图片地址
     * @param radius  圆角度数
     * @param errorid 错误图片
     */

    fun loadImageViewRoundedCorners(
        context: Context,
        view: ImageView,
        url: String,
        radius: Int,
        @DrawableRes errorid: Int
    ) {
        //设置图片圆角角度
        val roundedCorners = RoundedCorners(radius)
        val options = RequestOptions.bitmapTransform(roundedCorners)
        Glide.with(context)
            .load(url)
            .apply(options)
            .placeholder(R.drawable.douyu)
            .error(errorid)
            .into(view);
    }

    /**
     * 加载圆形图片
     *
     * @param context 上下文
     * @param view    图片控件
     * @param url     网络图片地址
     * @param errorid 错误图片
     */

    fun loadImageViewCircleCorners(
        context: Context,
        view: ImageView,
        url: String,
        @DrawableRes errorid: Int
    ) {
        val options = RequestOptions.circleCropTransform()
        Glide.with(context)
            .load(url)
            .apply(options)
            .placeholder(R.drawable.douyu)
            .error(errorid)
            .into(view);
    }

    /**
     * 加载圆形图片
     *
     * @param context 上下文
     * @param view    图片控件
     * @param url     网络图片地址
     * @param errorid 错误图片
     */

    fun loadImageViewCircleCorners(
        context: Context,
        view: ImageView,
        @DrawableRes url: Int,
        @DrawableRes errorid: Int
    ) {
        val options = RequestOptions.circleCropTransform()
        Glide.with(context)
            .load(url)
            .apply(options)
            .placeholder(R.drawable.douyu)
            .error(errorid)
            .into(view);
    }

    //清理磁盘缓存
    fun GuideClearDiskCache(mContext: Context?) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext!!).clearDiskCache()
    }

    //清理内存缓存
    fun GuideClearMemory(mContext: Context?) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext!!).clearMemory()
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
        image.recycle()
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

    fun readPictureDegree(tmpBitmap: Bitmap, path: String): Bitmap {
        val exifInterface = ExifInterface(path)
        val orientation = exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateToDegrees(tmpBitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateToDegrees(tmpBitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateToDegrees(tmpBitmap, 270)
            else -> tmpBitmap
        }
    }

    /**
     * 图片旋转
     *
     * @param tmpBitmap
     * @param degrees 旋转角度
     * @return
     */

    fun rotateToDegrees(tmpBitmap: Bitmap, degrees: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees.toFloat())
        val rotateBitmap =
            Bitmap.createBitmap(tmpBitmap, 0, 0, tmpBitmap.width, tmpBitmap.height, matrix, true)
        tmpBitmap.recycle()//将不需要的图片回收
        return rotateBitmap
    }
}