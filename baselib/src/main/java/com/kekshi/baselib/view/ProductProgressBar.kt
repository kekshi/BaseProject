package com.kekshi.baselib.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.LinearInterpolator
import java.text.DecimalFormat

/**
 * 含padding的处理
 * */
class ProductProgressBar @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {
    private lateinit var bgPaint: Paint
    private lateinit var progressPaint: Paint
    private lateinit var textPaint: Paint

    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private var mViewHeight: Int = 0
    /**
     * 进度
     */
    private var mProgress: Float = 0f
    //描述文字的高度
    private var textHeight: Int = 0
    //描述文字的宽度
    private var textWidth: Int = 0

    /**
     * 当前进度
     */
    private var currentProgress: Float = 0f

    /**
     * 进度动画
     */
    private lateinit var progressAnimator: ValueAnimator

    /**
     * 动画执行时间
     */
    private val duration = 1000L
    /**
     * 动画延时启动时间
     */
    private val startDelay = 500L
    /**
     * 进度条画笔的宽度
     */
    private var progressPaintWidth: Int = 0

    private var progressHeight: Int = 0

    /**
     * 进度条距离提示框的高度
     */
    private var progressMarginTop: Int = 0
    /**
     * 进度移动的距离
     */
    private var moveDis: Float = 0f

    private val textRect = Rect()

    private var textString = "已售0%"
    /**
     * 百分比文字字体大小
     */
    private var textPaintSize: Int = 0

    /**
     * 进度条背景颜色
     */
    private val bgColor = Color.GRAY
    /**
     * 进度条颜色
     */
    private val progressColor = -0x994ee


    private val bgRectF = RectF()
    private val progressRectF = RectF()

    /**
     * 圆角矩形的圆角半径
     */
    private var roundRectRadius: Float = 0f

    /**
     * 进度监听回调
     */
    private var progressListener: ProgressListener? = null

    init {
        initView()
        initPaint()
        initTextPaint()
    }

    private fun initView() {
        progressPaintWidth = dp2px(1)
        progressHeight = dp2px(3)
        roundRectRadius = dp2px(3).toFloat()
        textPaintSize = sp2px(10)
        textHeight = dp2px(10)
        progressMarginTop = dp2px(4)

        mViewHeight = textHeight + progressMarginTop + progressPaintWidth * 2 + progressHeight
    }

    private fun initTextPaint() {
        textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint.apply {
            textSize = textPaintSize.toFloat()
            color = progressColor
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
    }

    private fun initPaint() {
        bgPaint = getPaint(progressPaintWidth.toFloat(), bgColor, Paint.Style.FILL)
        progressPaint = getPaint(progressPaintWidth.toFloat(), progressColor, Paint.Style.FILL)
    }

    /**
     * 统一处理paint
     *
     * @param strokeWidth 画笔宽度
     * @param color       颜色
     * @param style       风格
     * @return paint
     */
    private fun getPaint(strokeWidth: Float, color: Int, style: Paint.Style): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.strokeWidth = strokeWidth //设置所画线条宽度
            this.color = color
            this.style = style
        }
    }

//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        super.onSizeChanged(w, h, oldw, oldh)
//        mWidth = w + paddingStart + paddingEnd
//    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        Log.d("ddddd", "width is :$width")

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(measureWidth(widthMode, width), measureHeight(heightMode, height))
    }

    private fun measureWidth(widthMode: Int, width: Int): Int {
        when (widthMode) {
            MeasureSpec.AT_MOST -> {
                mWidth = width
            }
            MeasureSpec.EXACTLY -> {
                mWidth = width
            }
        }
        return mWidth
    }

    private fun measureHeight(heightMode: Int, height: Int): Int {
        when (heightMode) {
            MeasureSpec.AT_MOST -> {
                mHeight = mViewHeight + paddingTop + paddingBottom
            }
            MeasureSpec.EXACTLY -> {
                mHeight = height + paddingTop + paddingBottom
            }
        }
        return mHeight
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawText(canvas, textString)

        drawBgProgress(canvas)

        drawProgress(canvas)
    }

    private fun drawText(canvas: Canvas?, textString: String) {
        textRect.left = moveDis.toInt()
        textRect.top = paddingTop
        textRect.right = (textPaint.measureText(textString) + moveDis).toInt()
        textRect.bottom = textHeight

        val fontMetricsInt = textPaint.fontMetricsInt
        //文字矩形的高度-文字的顶度和底部 /2  =文字基线的位置
        val baseline =
            (textRect.bottom + textRect.top - fontMetricsInt.bottom - fontMetricsInt.top) / 2

        canvas?.drawText(textString, textRect.centerX().toFloat(), baseline.toFloat(), textPaint)
    }

    private fun drawBgProgress(canvas: Canvas?) {
        bgRectF.left = paddingStart.toFloat()
        bgRectF.top = (paddingTop + textHeight + progressMarginTop).toFloat()
        bgRectF.right = (mWidth - paddingEnd).toFloat()
        bgRectF.bottom = bgRectF.top + progressHeight
        canvas?.drawRoundRect(bgRectF, roundRectRadius, roundRectRadius, bgPaint)
    }

    private fun drawProgress(canvas: Canvas?) {
        progressRectF.left = paddingStart.toFloat()
        progressRectF.top = (paddingTop + textHeight + progressMarginTop).toFloat()
        progressRectF.right = currentProgress
        progressRectF.bottom = progressRectF.top + progressHeight
        canvas?.drawRoundRect(progressRectF, roundRectRadius, roundRectRadius, progressPaint)
    }

    private fun initAnimation() {
        progressAnimator = ValueAnimator.ofFloat(0f, mProgress)
        progressAnimator.duration = duration
        progressAnimator.startDelay = startDelay
        progressAnimator.interpolator = LinearInterpolator()
        progressAnimator.addUpdateListener { value ->
            val animatedValue = value.animatedValue as Float
            textString = "已售" + formatNum(animatedValue.toInt()) + "%"
            currentProgress = animatedValue * mWidth / 100
            textWidth = textPaint.measureText(textString).toInt()
            progressListener?.currentProgressListener(animatedValue)
            //移动百分比提示框，只有当前进度到提示框中间位置之后开始移动，
            // 当进度框移动到最右边的时候停止移动，但是进度条还可以继续移动
            if (currentProgress >= textWidth && currentProgress <= mWidth) {
                moveDis = currentProgress - textWidth
            }
            invalidate()
        }
        if (!progressAnimator.isStarted) {
            progressAnimator.start()
        }
    }

    fun setProgress(progress: Float): ProductProgressBar {
        mProgress = progress
        initAnimation()
        return this
    }

    /**
     * 格式化数字
     *
     * @param money
     * @return
     */
    fun formatNum(money: Int): String {
        val format = DecimalFormat("0")
        return format.format(money.toLong())
    }

    /**
     * 回调接口
     */
    interface ProgressListener {
        fun currentProgressListener(currentProgress: Float)
    }

    /**
     * 回调监听事件
     *
     * @param listener
     * @return
     */
    fun setProgressListener(listener: ProgressListener): ProductProgressBar {
        progressListener = listener
        return this
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected fun dp2px(dpVal: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dpVal.toFloat(), resources.displayMetrics
        ).toInt()
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected fun sp2px(spVal: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, spVal.toFloat(), resources.displayMetrics
        ).toInt()
    }
}