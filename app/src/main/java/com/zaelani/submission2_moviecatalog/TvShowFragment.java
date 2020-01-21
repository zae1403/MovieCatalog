package com.zaelani.submission2_moviecatalog;


import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zaelani.submission2_moviecatalog.adapter.ListMovieAdapter;
import com.zaelani.submission2_moviecatalog.model.Movie;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private RecyclerView rvMovies;
    private ArrayList<Movie> movies = new ArrayList<>();

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
        rvMovies = view.findViewById(R.id.rv_movies);
        rvMovies.setHasFixedSize(true);

        movies.addAll(getMovies());
        showRecyclerList();

    }

    private ArrayList<Movie> getMovies() {
        String[] dataName = getResources().getStringArray(R.array.data_name_tvshow);
        String[] dataDescription = getResources().getStringArray(R.array.data_description_tvshow);
        String[] dataRating = getResources().getStringArray(R.array.data__rating_tvshow);
        TypedArray dataPhoto = getResources().obtainTypedArray(R.array.data_photo_tvshow);
        String dataDirector = getResources().getString(R.string.nol);
        String dataTitle = getResources().getString(R.string.title_tvshow);


        for (int i = 0; i < dataName.length; i++) {
            Movie movie = new Movie();
            movie.setName(dataName[i]);
            movie.setDescription(dataDescription[i]);
            movie.setPhoto(dataPhoto.getResourceId(i, -1));
            movie.setDirector(dataDirector);
            movie.setRating(dataRating[i]);
            movie.setTitle(dataTitle);

            movies.add(movie);

        }
        return movies;
    }

    private void showRecyclerList() {
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        ListMovieAdapter listMovieAdapter = new ListMovieAdapter(movies);
        rvMovies.setAdapter(listMovieAdapter);
    }


}
