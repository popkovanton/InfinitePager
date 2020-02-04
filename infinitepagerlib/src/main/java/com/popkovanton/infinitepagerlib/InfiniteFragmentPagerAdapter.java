package com.popkovanton.infinitepagerlib;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public abstract class InfiniteFragmentPagerAdapter extends FragmentStatePagerAdapter {

    public InfiniteFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }

    public int getActualPosition(int position) {
        return isEmpty() ? POSITION_NONE : position;
    }
}
