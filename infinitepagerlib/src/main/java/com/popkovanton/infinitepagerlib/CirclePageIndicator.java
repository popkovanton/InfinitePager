package com.popkovanton.infinitepagerlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class CirclePageIndicator extends View {

    private int mRadius;
    private int mMargin;
    private int mSelectedColor;
    private int mUnselectedColor;
    private boolean isClickable;
    private Paint mPaint;
    private int mCount = 0;
    private int mSelectedPosition = 0;
    private ViewPager mViewPager;

    private final ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            if (mViewPager == null) {
                return;
            }
            PagerAdapter adapter = mViewPager.getAdapter();
            if (adapter != null) {
                int selectedPos;
                if (adapter instanceof InfiniteFragmentPagerAdapter) {
                    selectedPos = ((InfiniteFragmentPagerAdapter) adapter).getActualPosition(position);
                } else {
                    selectedPos = position;
                }
                if (mSelectedPosition != selectedPos) {
                    mSelectedPosition = selectedPos;
                    invalidate();
                }
            }
        }
    };

    public CirclePageIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CirclePageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        float density = context.getResources().getDisplayMetrics().density;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CirclePageIndicator);
        try {
            mMargin = a.getDimensionPixelSize(R.styleable.CirclePageIndicator_marginBtwIndicators, (int) (4 * density));
            mRadius = a.getDimensionPixelSize(R.styleable.CirclePageIndicator_indicatorRadius, (int) (3 * density));
            mSelectedColor = a.getColor(R.styleable.CirclePageIndicator_selectedColor, Color.BLACK);
            mUnselectedColor = a.getColor(R.styleable.CirclePageIndicator_unselectedColor, Color.WHITE);
            isClickable = a.getBoolean(R.styleable.CirclePageIndicator_clickable, true);
        } finally {
            a.recycle();
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        final InfiniteFragmentPagerAdapter adapter = (InfiniteFragmentPagerAdapter) mViewPager.getAdapter();
        if (adapter != null)
            mCount = adapter.getCount();
        if (mCount > 0) {
            requestLayout();
        }
    }

    public void setUnselectedColor(int unselectedColor) {
        mUnselectedColor = unselectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        mSelectedColor = selectedColor;
    }

    @Override
    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }

    public void setRadius(int radius) {
        mRadius = radius;
        invalidate();
    }

    public int getRadius() {
        return mRadius;
    }

    public void setMargin(int margin) {
        mMargin = margin;
        invalidate();
    }

    public int getMargin() {
        return mMargin;
    }

    public void updateCount() {
        InfiniteFragmentPagerAdapter adapter = (InfiniteFragmentPagerAdapter) mViewPager.getAdapter();
        mCount = (adapter == null) ? 0 : adapter.getCount();
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getPaddingLeft() + getPaddingRight();
        int height = getPaddingTop() + getPaddingBottom();
        if (mCount > 0) {
            int diameter = 2 * mRadius;
            width += diameter;
            height += diameter;
            for (int i = 1; i < mCount; i++) {
                width += mMargin + diameter;
            }
        }
        setMeasuredDimension(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCount > 0) {
            int startX = getPaddingLeft();
            int startY = getPaddingTop();
            int diameter = 2 * mRadius;
            for (int i = 0; i < mCount - 1; i++) {
                mPaint.setColor(i == mSelectedPosition ? mSelectedColor : mUnselectedColor);
                canvas.drawCircle(startX + mRadius, startY + mRadius, mRadius, mPaint);
                startX += diameter + mMargin;
            }
            mPaint.setColor(mCount - 1 == mSelectedPosition ? mSelectedColor : mUnselectedColor);
            canvas.drawCircle(startX + mRadius, startY + mRadius, mRadius, mPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isClickable) {
                    int startX = getPaddingLeft();
                    int diameter = 2 * mRadius;
                    for (int i = 0; i < mCount; i++) {
                        if (event.getX() >= startX && event.getX() <= startX + diameter + mMargin) {
                            mViewPager.setCurrentItem(i);
                            return true;
                        }
                        startX += diameter + mMargin;
                    }
                }
                return true;
        }
        return true;
    }
}
