package com.kekshi.baseproject.widget.zdyview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.elvishew.xlog.XLog
import kotlin.math.min


//https://blog.csdn.net/mengks1987/article/details/77771465?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.add_param_isCf&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.add_param_isCf
class RoundView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) , Animation.AnimationListener {

    /**
     * 圆环宽度,默认半径的1／5
     */
    private var mRingWidth = 0f
    /**
     * 圆环颜色,默认 #CBCBCB
     */
    private var mRingColor = 0

    /**
     * 圆环半径,默认：Math.min(getHeight()/2,getWidth()/2)
     */
    private var mRadius = 0f
    /**
     * 底环画笔
     */
    private val mRingPaint = Paint()

    /**
     * 进度条圆环宽度
     */
    private var mProgressRingWidth = 0f
    /**
     * 进度条圆环开始颜色，进度条圆环是渐变的,默认
     */
    private var mProgressRingStartColor = 0
    /**
     * 进度条圆环结束颜色，进度条圆环是渐变的,默认
     */
    private var mProgressRingEndColor = 0
    /**
     * 进度条圆环Paint
     */
    private var mProgressRingPaint = Paint()
    /**
     * 进度条圆环渐变shader
     */
    private var mProgressRingShader: Shader? = null
    /**
     * 渐变色数组
     */
    private var arcColors: IntArray

    /**
     * 进度动画
     */
//    private val barAnimation: BarAnimation? = null
    /**
     * 抖动（缩放）动画
     */
    private val scaleAnimation: ScaleAnimation? = null
    /**
     * 是否正在改变
     */
    private val isAnimation = false
    init {
        val typedArray = context.obtainStyledAttributes(attrs, com.kekshi.baseproject.R.styleable.RoundView)
        mRingWidth =
            typedArray.getDimension(com.kekshi.baseproject.R.styleable.RoundView_ring_width, 0f)
        mRadius =
            typedArray.getDimension(com.kekshi.baseproject.R.styleable.RoundView_ring_radius, 0f)
        mRingColor =
            typedArray.getColor(
                com.kekshi.baseproject.R.styleable.RoundView_ring_color,
                Color.parseColor("#CBCBCB")
            )

        mProgressRingWidth =
            typedArray.getDimension(com.kekshi.baseproject.R.styleable.RoundView_progress_ring_width, 0f)
        mProgressRingStartColor = typedArray.getColor(
            com.kekshi.baseproject.R.styleable.RoundView_progress_ring_start_color,
            Color.parseColor("#f90aa9")
        )
        mProgressRingEndColor = typedArray.getColor(
            com.kekshi.baseproject.R.styleable.RoundView_progress_ring_end_color,
            Color.parseColor("#931c80")
        )
        typedArray.recycle()

        // 抗锯齿效果
        mRingPaint.isAntiAlias = true
        mRingPaint.style = Paint.Style.STROKE
        mRingPaint.color = mRingColor

        arcColors = intArrayOf(mProgressRingStartColor, mProgressRingEndColor)

        // 抗锯齿效果
        mProgressRingPaint.isAntiAlias = true
        mProgressRingPaint.style = Paint.Style.STROKE
        mProgressRingPaint.color = mRingColor
        // 圆形笔头
        mProgressRingPaint.strokeCap = Paint.Cap.ROUND
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        XLog.d("ddddd-onMeasure")

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        XLog.d("ddddd-onLayout。left:$left,top:$top,right:$right,bottom:$bottom")
        //如果未设置半径，则半径的值为view的宽、高一半的较小值
        mRadius = if (mRadius == 0f) min(width / 2, height / 2).toFloat() else mRadius
        //圆环的宽度默认为半径的1／5
        mRingWidth = if (mRingWidth == 0f) (mRadius / 5) else mRingWidth
        //圆环本身有宽度，不减去圆环宽度的一半，则圆环有一般宽度会显示在屏幕外面，
        mRadius -= mRingWidth / 2
        mRingPaint.strokeWidth = mRingWidth

        mProgressRingWidth = if (mProgressRingWidth == 0f) mRingWidth else mProgressRingWidth
        mProgressRingPaint.strokeWidth = mProgressRingWidth
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        XLog.d("ddddd-onSizeChanged。w:$w,h:$h,oldw:$oldw,oldh:$oldh")
    }

    private fun radianToAngle(radios: Float): Double {
        val aa = mProgressRingWidth / 2 / radios.toDouble()
        val asin = Math.asin(aa)
        return Math.toDegrees(asin)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        XLog.d("ddddd-onDraw")
        val x = (width / 2).toFloat()
        val y = (height / 2).toFloat()

        canvas.drawCircle(x, y, mRadius, mRingPaint)

        if (mProgressRingShader == null) {
            //扫描渐变
            mProgressRingShader = SweepGradient(x, y, arcColors, null)
            mProgressRingPaint.shader = mProgressRingShader
        }

        canvas.save()
        val radian = radianToAngle(mRadius)
        XLog.d("ddddd-radian:$radian")
        val degrees = Math.toDegrees(-2 * Math.PI / 360 * (90 + radian))
        XLog.d("ddddd-radian:$degrees")
        //逆时针旋转90度
        canvas.rotate(degrees.toFloat(), x, y)
        //从顶部开始 startAngle 是-90。从0开始有一半会画到边界外
        canvas.drawArc(
            mProgressRingWidth / 2,
            mProgressRingWidth / 2,
            width.toFloat() - mProgressRingWidth / 2,
            height.toFloat() - mProgressRingWidth / 2,
            radian.toFloat(),
            180f,
            false,
            mProgressRingPaint
        )
        canvas.restore()
    }

    override fun onAnimationEnd(animation: Animation?) {
    }

    override fun onAnimationStart(animation: Animation?) {
    }

    override fun onAnimationRepeat(animation: Animation?) {
    }
}