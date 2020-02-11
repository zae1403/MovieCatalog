package com.zaelani.submission_moviecatalog.adapter;

import android.content.Context;

import com.zaelani.submission_moviecatalog.ui.favorite.MoviesFavoriteFragment;
import com.zaelani.submission_moviecatalog.R;
import com.zaelani.submission_moviecatalog.ui.favorite.TvshowFavoriteFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FavoritePagerAdapter extends FragmentPagerAdapter {
    public Context context;

    public FavoritePagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text_1, R.string.tab_text_2
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;

        switch (position){
            case 0:
                fragment = new MoviesFavoriteFragment();
                break;
            case 1:
                fragment = new TvshowFavoriteFragment();
                break;
                default:
                    fragment = new MoviesFavoriteFragment();
                    break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}
