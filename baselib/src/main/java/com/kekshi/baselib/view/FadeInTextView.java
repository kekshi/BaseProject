package com.kekshi.baselib.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * <p>
 * 字符串逐字显示的view
 * fadeInTextView
 * .setTextString("自己的字符串")
 * .startFadeInAnimation()
 * .setTextAnimationListener(new FadeInTextView.TextAnimationListener() {
 *
 * @Override public void animationFinish() {
 * <p>
 * }
 * });
 */
public class FadeInTextView extends AppCompatTextView {
    private final String TAG = "FadeInTextView";
    private Rect textRect = new Rect();

    private StringBuffer stringBuffer = new StringBuffer();

    private String[] arr;

    private int textCount;

    private int currentIndex = -1;

    /**
     * 每个字出现的时间
     */
    private int duration = 300;
    private ValueAnimator textAnimation;
    private TextAnimationListener textAnimationListener;

    public FadeInTextView(Context context) {
        this(context, null);
    }

    public FadeInTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (stringBuffer != null) {
//            drawText(canvas, stringBuffer.toString());
//        }
    }

    private void drawText(Canvas canvas, String textString) {
        //获取 padding值，需要自己处理
        textRect.left = getPaddingLeft();
        textRect.top = getPaddingTop();
        textRect.right = getWidth() - getPaddingRight();
        textRect.bottom = getHeight() - getPaddingBottom();

        Paint.FontMetricsInt fontMetrics = getPaint().getFontMetricsInt();
        Log.d(TAG, "fontMetrics.bottom is :" + fontMetrics.bottom);
        Log.d(TAG, "fontMetrics.top is :" + fontMetrics.top);
        int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        //文字绘制到整个布局的中心位置
        canvas.drawText(textString, getPaddingLeft(), baseline, getPaint());
    }

    private void initAnimation() {
        Log.d(TAG, "initAnimation 被执行");
        //从0到textCount - 1  是设置从第一个字到最后一个字的变化因子
        textAnimation = ValueAnimator.ofInt(0, textCount - 1);
        //执行总时间就是每个字的时间乘以字数
        textAnimation.setDuration(textCount * duration);
        //匀速显示文字
        textAnimation.setInterpolator(new LinearInterpolator());
        textAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取动画执行的值
                int index = (int) animation.getAnimatedValue();
                //过滤去重，保证每个字只重绘一次
                if (currentIndex != index) {
                    stringBuffer.append(arr[index]);
                    currentIndex = index;
                    //所有文字都显示完成之后进度回调结束动画
                    if (currentIndex == (textCount - 1)) {
                        if (textAnimationListener != null) {
                            textAnimationListener.animationFinish();
                        }
                    }
                    setText(stringBuffer.toString());
//                    invalidate();
                }
            }
        });
    }

    public FadeInTextView setTextString(String textString) {
        if (textString != null) {
            Log.d(TAG, "setTextString 被执行");
            //总字数
            textCount = textString.length();
            //存放单个字的数组
            arr = new String[textCount];
            for (int i = 0; i < textCount; i++) {
                arr[i] = textString.substring(i, i + 1);
            }
            initAnimation();
        }
        return this;
    }

    public FadeInTextView startFadeInAnimation() {
        if (textAnimation != null) {
            Log.d(TAG, "startFadeInAnimation 被执行");
            stringBuffer.setLength(0);
            currentIndex = -1;
            textAnimation.start();
        }
        return this;
    }

    public FadeInTextView stopFadeInAnimation() {
        if (textAnimation != null) {
            textAnimation.end();
        }
        return this;
    }

    public FadeInTextView setTextAnimationListener(TextAnimationListener textAnimationListener) {
        this.textAnimationListener = textAnimationListener;
        return this;
    }

    /**
     * 回调接口
     */
    public interface TextAnimationListener {
        void animationFinish();
    }
}
