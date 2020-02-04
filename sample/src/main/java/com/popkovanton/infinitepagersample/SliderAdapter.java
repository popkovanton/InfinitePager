package com.popkovanton.infinitepagersample;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.popkovanton.infinitepagerlib.InfiniteFragmentPagerAdapter;

public class SliderAdapter extends InfiniteFragmentPagerAdapter {

    private int counter = 5;

    SliderAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return SliderFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return counter;
    }

    public void decPages() {
        if(counter >= 1) counter--;
        notifyDataSetChanged();
    }

    public void incPages() {
        counter++;
        notifyDataSetChanged();
    }


    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
