package com.zaelani.submission_moviecatalog.adapter;

import android.content.Context;

import com.zaelani.submission_moviecatalog.R;
import com.zaelani.submission_moviecatalog.ui.home.TvShowFragment;
import com.zaelani.submission_moviecatalog.ui.home.MoviesFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public SectionPagerAdapter(Context mContext, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mContext = mContext;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text_1, R.string.tab_text_2
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new MoviesFragment();

                break;
            case 1:
                fragment = new TvShowFragment();
                break;

            default:
                fragment = new MoviesFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }

}
