package com.popkovanton.infinitepager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.popkovanton.infinitepagerlib.InfiniteFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends InfiniteFragmentPagerAdapter {

    public SliderAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    protected List<Fragment> initFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(SliderFragment.newInstance(1));
        fragments.add(SliderFragment.newInstance(2));
        fragments.add(SliderFragment.newInstance(3));
        fragments.add(SliderFragment.newInstance(4));
        fragments.add(SliderFragment.newInstance(5));
        return fragments;
    }


   /* public int getItemPosition(Object object) {
        return POSITION_NONE;
    }*/

}
