package com.popkovanton.infinitepagersample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.popkovanton.infinitepagerlib.InfiniteViewPager;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    private SliderAdapter mImageSliderAdapter;
    private InfiniteViewPager mImageSliderView;
    private Button buttonDecrease, buttonIncrease;

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

        buttonDecrease = findViewById(R.id.buttonDecrease);
        buttonDecrease.setOnClickListener(this);
        buttonIncrease = findViewById(R.id.buttonIncrease);
        buttonIncrease.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonIncrease:
                mImageSliderAdapter.incPages();
                break;
            case R.id.buttonDecrease:
                mImageSliderAdapter.decPages();
                break;
        }
        mImageSliderView.updateIndicator();
    }
}
