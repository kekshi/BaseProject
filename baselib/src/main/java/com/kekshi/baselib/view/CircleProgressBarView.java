package com.kekshi.baselib.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.kekshi.baselib.R;

public class CircleProgressBarView extends View {

    /**
     * 圆心x坐标
     */
    private float centerX;
    /**
     * 圆心y坐标
     */
    private float centerY;
    /**
     * 圆的半径
     */
    private float radius;

    /**
     * 进度
     */
    private float mProgress;

    /**
     * 当前进度
     */
    private float currentProgress;
    /**
     * 默认圆环的宽度
     */
    private int defaultStrokeWidth = 10;
    /**
     * 圆形背景画笔宽度
     */
    private int circleBgStrokeWidth = defaultStrokeWidth;
    /**
     * 圆形进度画笔宽度
     */
    private int progressStrokeWidth = defaultStrokeWidth;
    /**
     * 进度条背景颜色
     */
    private int circleBgColor = 0xFFe1e5e8;
    /**
     * 进度条颜色
     */
    private int progressColor = 0xFFf66b12;
    /**
     * 进度动画
     */
    private ValueAnimator progressAnimator;
    /**
     * 动画执行时间
     */
    private int duration = 1000;
    /**
     * 动画延时启动时间
     */
    private int startDelay = 500;
    /**
     * 圆形进度条底色画笔
     */
    private Paint circleBgPaint;
    /**
     * 圆形进度条进度画笔
     */
    private Paint progressPaint;
    /**
     * 扇形所在矩形
     */
    private RectF rectF = new RectF();

    private boolean isDrawCenterProgressText;
    private int centerProgressTextSize = 10;
    private int centerProgressTextColor = Color.BLACK;

    private Paint centerProgressTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private ProgressListener progressListener;

    public CircleProgressBarView(Context context) {
        this(context, null);
    }

    public CircleProgressBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        getAttr(context, attrs);
        initPaint();
        initTextPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        widthSize = resolveSize(widthSize, widthMeasureSpec) - getPaddingStart() - getPaddingEnd();
        heightSize = resolveSize(heightSize, heightMeasureSpec);
        // 再用 setMeasuredDimension(width, height) 来保存最终的宽度和高度
        setMeasuredDimension(widthSize, heightSize);


        centerX = widthSize / 2;
        centerY = heightSize / 2;
        radius = Math.min(widthSize, heightSize) / 2 - Math.max(circleBgStrokeWidth, progressStrokeWidth);
        Log.d("dddddd", " widthSize() is :" + MeasureSpec.getSize(widthMeasureSpec));
        Log.d("dddddd", " heightSize() is :" + MeasureSpec.getSize(heightMeasureSpec));
        Log.d("dddddd", " getMeasuredWidth() is :" + getMeasuredWidth());
        Log.d("dddddd", " getMeasuredHeight() is :" + getMeasuredHeight());
        Log.d("dddddd", " resolveSizeWidth() is :" + widthSize);
        Log.d("dddddd", " resolveSizeHeight() is :" + heightSize);

        rectF.set(centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius);
    }

    private int getSize(int size, int measureSpec) {
        final int specMode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);
        final int result;
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                if (specSize < size) {
                    result = specSize;
                } else {
                    result = size;
                }
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                result = size;
        }
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(centerX, centerY, radius, circleBgPaint);
        canvas.drawArc(rectF, 90, currentProgress, false, progressPaint);
        if (isDrawCenterProgressText) {
            drawCenterProgressText(canvas, (int) mProgress + "%");
        }
    }

    private void drawCenterProgressText(Canvas canvas, String currentProgress) {
        Paint.FontMetricsInt fontMetricsInt = centerProgressTextPaint.getFontMetricsInt();
        int baseline = (int) ((rectF.bottom + rectF.top - fontMetricsInt.bottom - fontMetricsInt.top) / 2);
        //文字绘制到整个布局的中心位置
        canvas.drawText(currentProgress, rectF.centerX(), baseline, centerProgressTextPaint);
    }

    private void getAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBarView);
        circleBgStrokeWidth = typedArray.getDimensionPixelOffset(R.styleable.CircleProgressBarView_circleBgStrokeWidth, defaultStrokeWidth);
        progressStrokeWidth = typedArray.getDimensionPixelOffset(R.styleable.CircleProgressBarView_progressStrokeWidth, defaultStrokeWidth);

        circleBgColor = typedArray.getColor(R.styleable.CircleProgressBarView_circleBgColor, circleBgColor);
        progressColor = typedArray.getColor(R.styleable.CircleProgressBarView_progressColor, progressColor);

        duration = typedArray.getColor(R.styleable.CircleProgressBarView_circleAnimationDuration, duration);

        isDrawCenterProgressText = typedArray.getBoolean(R.styleable.CircleProgressBarView_isDrawCenterProgressText, false);

        centerProgressTextColor = typedArray.getColor(R.styleable.CircleProgressBarView_centerProgressTextColor, centerProgressTextColor);
        centerProgressTextSize = typedArray.getDimensionPixelOffset(R.styleable.CircleProgressBarView_centerProgressTextSize, sp2px(centerProgressTextSize));

        typedArray.recycle();
    }

    public void startProgressAnimation() {
        progressAnimator.start();
    }

    public void pauseProgressAnimation() {
        progressAnimator.pause();
    }

    public void resumeProgressAnimation() {
        progressAnimator.resume();
    }

    public void stopProgressAnimation() {
        progressAnimator.end();
    }

    /**
     * 传入一个进度值，从0到progress动画变化
     *
     * @param progress
     * @return
     */
    public CircleProgressBarView setProgressWithAnimation(float progress) {
        mProgress = progress;
        initAnimation();
        return this;
    }

    /**
     * 实时进度，适用于下载进度回调时候之类的场景
     *
     * @param progress
     * @return
     */
    public CircleProgressBarView setCurrentProgress(float progress) {
        mProgress = progress;
        currentProgress = progress * 360 / 100;
        invalidate();
        return this;
    }

    private void initAnimation() {
        progressAnimator = ValueAnimator.ofFloat(0, mProgress);
        progressAnimator.setDuration(duration);
        progressAnimator.setStartDelay(startDelay);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mProgress = value;
                currentProgress = value * 360 / 100;

                if (progressListener != null) {
                    progressListener.currentProgressListener(roundTwo(value));
                }
                invalidate();
            }
        });
    }

    private void initPaint() {
        circleBgPaint = getPaint(circleBgStrokeWidth, circleBgColor);

        progressPaint = getPaint(progressStrokeWidth, progressColor);
    }

    private void initTextPaint() {
        centerProgressTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerProgressTextPaint.setTextSize(centerProgressTextSize);
        centerProgressTextPaint.setColor(centerProgressTextColor);
        centerProgressTextPaint.setTextAlign(Paint.Align.CENTER);
        centerProgressTextPaint.setAntiAlias(true);
    }

    private Paint getPaint(int strokeWidth, int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);

        return paint;
    }

    public CircleProgressBarView setProgressListener(ProgressListener listener) {
        progressListener = listener;
        return this;
    }

    public interface ProgressListener {
        void currentProgressListener(float currentProgress);
    }

    /**
     * 将一个小数四舍五入，保留两位小数返回
     *
     * @param originNum
     * @return
     */
    public static float roundTwo(float originNum) {
        return (float) (Math.round(originNum * 10) / 10.00);
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }
}
