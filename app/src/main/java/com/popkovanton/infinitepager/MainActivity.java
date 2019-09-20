package com.popkovanton.infinitepager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.popkovanton.infinitepagerlib.InfiniteViewPager;

public class MainActivity extends AppCompatActivity {
    InfiniteViewPager mImageSliderView;
    private SliderAdapter mImageSliderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupImageSlider();
    }

    private void setupImageSlider() {
        mImageSliderView = findViewById(R.id.infiniteSlider);
        mImageSliderAdapter = new SliderAdapter(getSupportFragmentManager());
        mImageSliderView.setAdapter(mImageSliderAdapter);
    }
}
