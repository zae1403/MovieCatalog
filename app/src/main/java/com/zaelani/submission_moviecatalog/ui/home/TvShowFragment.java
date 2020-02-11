package com.zaelani.submission_moviecatalog.ui.home;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.zaelani.submission_moviecatalog.R;
import com.zaelani.submission_moviecatalog.adapter.ListMovieAdapter;
import com.zaelani.submission_moviecatalog.model.MovieItems;
import com.zaelani.submission_moviecatalog.ui.DetailMovieActivity;
import com.zaelani.submission_moviecatalog.viewmodel.TvShowViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements ListMovieAdapter.AdapterOnClick {
    private ProgressBar progressBarTvShow;
    private ListMovieAdapter adapter;
    private TvShowViewModel tvShowViewModel;


    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvMovies = view.findViewById(R.id.recycler_view);
        rvMovies.setHasFixedSize(true);

        progressBarTvShow = view.findViewById(R.id.progress_bar_tvshow);

        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ListMovieAdapter();
        adapter.notifyDataSetChanged();
        rvMovies.setAdapter(adapter);

        tvShowViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvShowViewModel.class);

        tvShowViewModel.setListMovies();
        //showLoading(true);

        tvShowViewModel.getListMovies().observe(getViewLifecycleOwner(), new Observer<ArrayList<MovieItems>>() {
            @Override
            public void onChanged(ArrayList<MovieItems> movieItems) {
                if (movieItems != null){
                    adapter.setMovies(movieItems, TvShowFragment.this, false,"tvshow");
                }
            }
        });

        tvShowViewModel.isLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != null){
                    showLoading(aBoolean);
                }
            }
        });
    }

    private void showLoading(Boolean state){
        if (state){
            progressBarTvShow.setVisibility(View.VISIBLE);
        }else {
            progressBarTvShow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(MovieItems movieItems) {
        Intent intent = new Intent(getContext(), DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieItems);
        intent.putExtra(DetailMovieActivity.EXTRA_STATE,"tvshow");
        intent.putExtra(DetailMovieActivity.EXTRA_TITLE, getString(R.string.detail_tvshow));
        startActivity(intent);
    }
}
