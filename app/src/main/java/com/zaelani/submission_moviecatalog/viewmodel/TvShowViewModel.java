package com.zaelani.submission_moviecatalog.viewmodel;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zaelani.submission_moviecatalog.BuildConfig;
import com.zaelani.submission_moviecatalog.model.MovieItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import cz.msebera.android.httpclient.Header;

public class TvShowViewModel extends ViewModel {
    private static final String API_KEY = BuildConfig.TheMovieDBApi;
    private MutableLiveData<ArrayList<MovieItems>> listMovies = new MutableLiveData<>();

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MutableLiveData<ArrayList<MovieItems>> getListMovies() {
        return listMovies;
    }

    public void setListMovies() {
        isLoading.postValue(true);
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MovieItems> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";
        final String urlPhoto = BuildConfig.UrlPhotoMovie;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItems = new MovieItems();
                        movieItems.setName(movie.getString("original_name"));
                        movieItems.setDescription(movie.getString("overview"));
                        double rating = movie.getDouble("vote_average");
                        movieItems.setRating(new DecimalFormat("#.#").format(rating));
                        String path = movie.getString("poster_path");
                        movieItems.setPhoto(urlPhoto + path);
                        listItems.add(movieItems);
                    }
                    isLoading.postValue(false);
                    listMovies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception: ", e.getMessage());
                    isLoading.postValue(false);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Exception: ", error.getMessage());
                isLoading.postValue(false);

            }
        });

    }
}
