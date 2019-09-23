package com.popkovanton.infinitepagersample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.popkovanton.infinitepagerlib.InfiniteViewPager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupImageSlider();
    }

    private void setupImageSlider() {
        InfiniteViewPager mImageSliderView = findViewById(R.id.infiniteSlider);
        SliderAdapter mImageSliderAdapter = new SliderAdapter(getSupportFragmentManager());
        mImageSliderView.setAdapter(mImageSliderAdapter);
    }
}
