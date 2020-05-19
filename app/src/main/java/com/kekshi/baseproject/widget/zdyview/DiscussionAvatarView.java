package com.kekshi.baseproject.widget.zdyview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.kekshi.baselib.utils.DensityUtils;
import com.kekshi.baseproject.R;

import java.util.List;

/**
 * https://github.com/hnsycsxhzcsh/DiscussionAvatarView
 * 控制堆叠头像重叠大小的控件
 */
public class DiscussionAvatarView extends ViewGroup {
    /**
     * 头像的半径
     */
    private int mRadius;
    /**
     * 头像间的距离
     */
    private float mSpace;
    /**
     * 是否最后一个显示完全
     * true 则从左往右添加控件；false 从右往左添加控件。
     */
    private boolean mIsLastComplete;
    /**
     * 最大头像数目
     */
    private int mMaxCount;

    public DiscussionAvatarView(Context context) {
        this(context, null);
    }

    public DiscussionAvatarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiscussionAvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DiscussionAvatarView);
        if (typedArray != null) {
            mRadius = typedArray.getDimensionPixelSize(R.styleable.DiscussionAvatarView_radius, DensityUtils.INSTANCE.dp2px(13));
            mSpace = typedArray.getFloat(R.styleable.DiscussionAvatarView_space, 0.5f);
            mMaxCount = typedArray.getInt(R.styleable.DiscussionAvatarView_maxCount, 6);
            mIsLastComplete = typedArray.getBoolean(R.styleable.DiscussionAvatarView_isLastComplete, true);

            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;
        int childCount = getChildCount();
        if (childCount > mMaxCount) {
            return;
        }
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            LayoutParams params = child.getLayoutParams();
            params.width = 2 * mRadius;//宽为圆的直径
            params.height = params.width;//宽高相等
            child.setLayoutParams(params);

            // 测量子View的宽和高,测量之后才可以获取子view的宽高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if (i == 0) {
                width = width + childWidth;
            } else {
                width = (int) (width + childWidth * mSpace);
            }
            height = Math.max(height, childHeight);
        }
        //如果是 EXACTLY(精确值：match_parent和具体值) 使用测量宽和高，否则使用自己设置的宽和高
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize : width, (heightMode == MeasureSpec.EXACTLY) ? heightSize : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();

        int left = 0;
        int top = 0;
        int right = 0;
        for (int i = 0; i < childCount; i++) {
            View child;
            //mIsLastComplete true，则从左往右添加控件，否则就是从右往左添加控件
            if (mIsLastComplete) {
                child = getChildAt(i);
            } else {
                child = getChildAt(childCount - i - 1);
            }
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (i == 0) {
                right = right + childWidth;
            } else {
                right = (int) (right + childWidth * mSpace);
            }
            child.layout(left, top, right, childHeight);
            left = (int) (left + childWidth * mSpace);
        }
    }

    /**
     * 添加子view
     *
     * @param datas
     */
    public void addDatas(List<View> datas) {
        if (datas == null) {
            return;
        }
        removeAllViews();
        int size = datas.size();
        mMaxCount = size;
        for (int i = 0; i < size; i++) {
            addView(datas.get(i));
        }
    }

    /**
     * 添加头像
     *
     * @param view
     */
    public void addData(View view) {
        if (view == null) {
            return;
        }
        if (mMaxCount <= 0) {
            return;
        }
        int childCount = getChildCount();
        if (mIsLastComplete) {
            addView(view);
        } else {
            addView(view, 0);
        }
    }
}
