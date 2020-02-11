package com.zaelani.submission_moviecatalog.ui.favorite;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zaelani.submission_moviecatalog.R;
import com.zaelani.submission_moviecatalog.adapter.ListMovieAdapter;
import com.zaelani.submission_moviecatalog.database.MovieHelper;
import com.zaelani.submission_moviecatalog.model.MovieItems;
import com.zaelani.submission_moviecatalog.ui.DetailMovieActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvshowFavoriteFragment extends Fragment implements ListMovieAdapter.AdapterOnClick, LoadTvshowCallback{
    private ProgressBar progressBar;
    private MovieHelper movieHelper;
    private ArrayList<MovieItems> listTvshow;
    private ListMovieAdapter adapter = new ListMovieAdapter();

    private static final String EXTRA_SAVE = "extra_save";


    public TvshowFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progress_bar_tvshow);

        RecyclerView rvFavoriteTvshow = view.findViewById(R.id.recycler_view);
        rvFavoriteTvshow.setHasFixedSize(true);

        movieHelper = new MovieHelper(getContext());
        movieHelper.openMovie();

        rvFavoriteTvshow.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
        rvFavoriteTvshow.setAdapter(adapter);

        listTvshow = movieHelper.getAllFavoriteTvshow();
//        adapter.setMovies(listTvshow, TvshowFavoriteFragment.this, true,"tvshow");


        if (savedInstanceState == null){
            new LoadTvshowAsync(movieHelper, this).execute();
        }else {
            ArrayList<MovieItems> movies = savedInstanceState.getParcelableArrayList(EXTRA_SAVE);
            if (movies != null){
                adapter.setMovies(movies, TvshowFavoriteFragment.this, true,"tvshow");
            }
        }
    }

    @Override
    public void onClick(MovieItems movieItems) {
        Intent intent = new Intent(getContext(), DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieItems);
        intent.putExtra(DetailMovieActivity.EXTRA_STATE, "tvshow");
        intent.putExtra(DetailMovieActivity.EXTRA_TITLE, getString(R.string.detail_tvshow));
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_SAVE, listTvshow);

    }

    @Override
    public void preExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<MovieItems> listTvshow) {
        progressBar.setVisibility(View.GONE);
        if (listTvshow.size() > 0 ){
            adapter.setMovies(listTvshow, this, true,"tvshow");
        }else {
            adapter.setMovies(new ArrayList<MovieItems>(),this,true,"tvshow");
            Toast.makeText(getContext(), getString(R.string.doesnt_exist), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieHelper.closeMovie();
    }

    static class LoadTvshowAsync extends AsyncTask<Void,Void,ArrayList<MovieItems>> {
        private final WeakReference<MovieHelper> weakTvshowHelper;
        private final WeakReference<LoadTvshowCallback> weakTvshowCallback;

        LoadTvshowAsync(MovieHelper movieHelper, LoadTvshowCallback callback) {
            this.weakTvshowHelper = new WeakReference<>(movieHelper);
            this.weakTvshowCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakTvshowCallback.get().preExecute();
        }

        @Override
        protected ArrayList<MovieItems> doInBackground(Void... voids) {
            return weakTvshowHelper.get().getAllFavoriteTvshow();
        }

        @Override
        protected void onPostExecute(ArrayList<MovieItems> movieItems) {
            super.onPostExecute(movieItems);
            weakTvshowCallback.get().postExecute(movieItems);
        }
    }
}

interface LoadTvshowCallback{
    void preExecute();
    void postExecute(ArrayList<MovieItems> listTvshow);
}

