package com.popkovanton.infinitepagerlib;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public abstract class InfiniteFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragments;

    public InfiniteFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragments = initFragments();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return getFragments().get(position);
    }

    @Override
    public int getCount() {
        return fragments != null ? fragments.size() : 0;
    }

    protected abstract List<Fragment> initFragments();

    protected List<Fragment> getFragments() {
        return fragments;
    }


    public boolean isEmpty() {
        return getCount() == 0;
    }

    public int getActualPosition(int position) {
        return isEmpty() ? POSITION_NONE : position;
    }
}
