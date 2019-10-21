package com.popkovanton.infinitepagerlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.lang.ref.WeakReference;

public class InfiniteViewPager extends FrameLayout {
    private static final long DEFAULT_SLIDE_DURATION = 4000;
    public static final int DEFAULT_INDICATOR_VISIBILITY = 0;
    private final int DEFAULT_GRAVITY = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;

    private int mIndicatorGravity = DEFAULT_GRAVITY;
    private int indicatorMarginVertical;
    private int indicatorMarginHorizontal;
    private int indicatorVisibility = DEFAULT_INDICATOR_VISIBILITY;

    private boolean isAspectRatio;
    private float mAspectRatio;

    private ViewPager mViewPager;
    private CirclePageIndicator mIndicator;

    private long mAutoSlideDuration = DEFAULT_SLIDE_DURATION;

    private boolean mIsAutoSlideEnabled = false;

    private AutoSlider mAutoSlider;

    public InfiniteViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InfiniteViewPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.pager_layout, this);
        mViewPager = findViewById(R.id.pager);
        mIndicator = findViewById(R.id.indicator);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InfiniteViewPager);
        try {
            isAspectRatio = a.getBoolean(R.styleable.InfiniteViewPager_isAspectRatio, false);
            mAspectRatio = a.getFloat(R.styleable.InfiniteViewPager_aspectRatio, 0.32f);
            mIsAutoSlideEnabled = a.getBoolean(R.styleable.InfiniteViewPager_autoSlide, false);
            mAutoSlideDuration = a.getInt(R.styleable.InfiniteViewPager_autoSlideDuration, (int) DEFAULT_SLIDE_DURATION);
            indicatorMarginVertical = a.getDimensionPixelSize(R.styleable.InfiniteViewPager_indicatorMarginVertical, getResources().getDimensionPixelSize(R.dimen.default_indicator_margin_vertical));
            indicatorMarginHorizontal = a.getDimensionPixelSize(R.styleable.InfiniteViewPager_indicatorMarginHorizontal, getResources().getDimensionPixelSize(R.dimen.default_indicator_margin_horizontal));
            setIndicatorGravity(a.getInt(R.styleable.InfiniteViewPager_indicatorGravity, mIndicatorGravity));

            indicatorVisibility = a.getInt(R.styleable.InfiniteViewPager_indicatorVisibility, indicatorVisibility);

            setIndicatorVisibility(indicatorVisibility);
            if (indicatorVisibility == View.VISIBLE) {
                int unselectedColor = a.getColor(R.styleable.InfiniteViewPager_unselectedColor, 0);
                if (unselectedColor != 0) {
                    setUnselectedColor(unselectedColor);
                }
                int selectedColor = a.getColor(R.styleable.InfiniteViewPager_selectedColor, 0);
                if (selectedColor != 0) {
                    setSelectedColor(selectedColor);
                }
                int radius = a.getDimensionPixelSize(R.styleable.InfiniteViewPager_radius, 0);
                if (radius != 0) {
                    setRadius(radius);
                }
                int margin = a.getDimensionPixelSize(R.styleable.InfiniteViewPager_margin, 0);
                if (margin != 0) {
                    setMargin(margin);
                }
            }
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isAspectRatio) {
            int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
            int measuredHeight = Math.round(mAspectRatio * measuredWidth);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAutoSlide();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAutoSlide();
    }

    public void setAdapter(PagerAdapter adapter) {
        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager);
    }

    public void startAutoSlide() {
        if (mIsAutoSlideEnabled) {
            if (mAutoSlider == null) {
                mAutoSlider = new AutoSlider(this);
            }
            postDelayed(mAutoSlider, mAutoSlideDuration);
        }
    }

    public void stopAutoSlide() {
        if (mAutoSlider != null) {
            removeCallbacks(mAutoSlider);
        }
    }

    public void setAutoSlideEnabled(boolean enable) {
        mIsAutoSlideEnabled = enable;
    }

    private void slide() {
        if (mViewPager != null && mViewPager.getAdapter() != null) {
            int next = mViewPager.getCurrentItem() + 1;
            if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1) {
                mViewPager.setCurrentItem(0, false);
            } else {
                mViewPager.setCurrentItem(next, true);
            }
            if (mIsAutoSlideEnabled && mAutoSlider != null) {
                mViewPager.postDelayed(mAutoSlider, mAutoSlideDuration);
            }
        }
    }

    public void setIndicatorGravity(int gravity) {
        mIndicatorGravity = gravity;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = mIndicatorGravity;
        params.setMargins(indicatorMarginHorizontal, indicatorMarginVertical, indicatorMarginHorizontal, indicatorMarginVertical);
        mIndicator.setLayoutParams(params);
    }

    public void setIndicatorVisibility(int visibility) {
        mIndicator.setVisibility(visibility);
    }

    public void setUnselectedColor(int unselectedColor) {
        mIndicator.setUnselectedColor(unselectedColor);
    }

    public void setSelectedColor(int selectedColor) {
        mIndicator.setSelectedColor(selectedColor);
    }

    public void setRadius(int radius) {
        mIndicator.setRadius(radius);
    }

    public void setMargin(int margin) {
        mIndicator.setMargin(margin);
    }

    public void setAspectRatio(boolean isAspectRatio, float mAspectRatio) {
        this.isAspectRatio = isAspectRatio;
        this.mAspectRatio = mAspectRatio;
    }

    /**
     * {@link AutoSlider}
     */
    private static final class AutoSlider implements Runnable {

        private final WeakReference<InfiniteViewPager> mRef;

        AutoSlider(InfiniteViewPager pageSliderView) {
            mRef = new WeakReference<>(pageSliderView);
        }

        @Override
        public void run() {
            final InfiniteViewPager pageSliderView = mRef.get();
            if (pageSliderView != null) {
                pageSliderView.slide();
            }
        }
    }
}
