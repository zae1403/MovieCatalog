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
public class MoviesFavoriteFragment extends Fragment implements ListMovieAdapter.AdapterOnClick, LoadMoviesCallback {

    private ProgressBar progressBar;
    private MovieHelper movieHelper;
    private ListMovieAdapter adapter = new ListMovieAdapter();
    private ArrayList<MovieItems> listMovies;

    private static final String EXTRA_SAVE = "extra_save";

    public MoviesFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar_movie);
        RecyclerView rvMoviesFavorite = view.findViewById(R.id.recycler_view);
        rvMoviesFavorite.setHasFixedSize(true);

        movieHelper = new MovieHelper(getContext());
        movieHelper.openMovie();

        rvMoviesFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
        rvMoviesFavorite.setAdapter(adapter);

        listMovies = movieHelper.getAllFavoriteMovie();
        if (savedInstanceState == null){
            new LoadMoviesAsync(movieHelper, this).execute();
        }else {
            ArrayList<MovieItems> movies = savedInstanceState.getParcelableArrayList(EXTRA_SAVE);
            if (movies != null){
                adapter.setMovies(movies,this,true,"movies");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieHelper.closeMovie();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_SAVE, listMovies);
    }

    @Override
    public void onClick(MovieItems movieItems) {
        Intent intent = new Intent(getContext(), DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieItems);
        intent.putExtra(DetailMovieActivity.EXTRA_STATE, "movies");
        intent.putExtra(DetailMovieActivity.EXTRA_TITLE, getString(R.string.detail_tvshow));
        startActivity(intent);
    }

    @Override
    public void preExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<MovieItems> listMovies) {
        progressBar.setVisibility(View.GONE);
        if (listMovies.size() > 0) {
            adapter.setMovies(listMovies, this, true, "movie");
        } else {
            adapter.setMovies(new ArrayList<MovieItems>(), this, true, "movie");
            Toast.makeText(getContext(), getString(R.string.doesnt_exist), Toast.LENGTH_SHORT).show();
        }

    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<MovieItems>> {
        private final WeakReference<MovieHelper> weakMovieHelper;
        private final WeakReference<LoadMoviesCallback> weakLoadMovies;

        private LoadMoviesAsync(MovieHelper movieHelper, LoadMoviesCallback callback) {
            weakMovieHelper = new WeakReference<>(movieHelper);
            weakLoadMovies = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakLoadMovies.get().preExecute();
        }

        @Override
        protected ArrayList<MovieItems> doInBackground(Void... voids) {
            return weakMovieHelper.get().getAllFavoriteMovie();
        }

        @Override
        protected void onPostExecute(ArrayList<MovieItems> movieItems) {
            super.onPostExecute(movieItems);
            weakLoadMovies.get().postExecute(movieItems);
        }
    }
}

interface LoadMoviesCallback {
    void preExecute();

    void postExecute(ArrayList<MovieItems> listMovies);
}
