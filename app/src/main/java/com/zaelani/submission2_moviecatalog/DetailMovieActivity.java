package com.zaelani.submission2_moviecatalog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zaelani.submission2_moviecatalog.model.Movie;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        TextView tvName = findViewById(R.id.tv_detail_name);
        TextView tvRating = findViewById(R.id.tv_detail_rating);
        TextView tvDescription = findViewById(R.id.tv_detail_description);
        TextView tvEditor = findViewById(R.id.tv_detail_editor);
        ImageView iv_photo = findViewById(R.id.img_photo);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        tvName.setText(movie.getName());
        tvDescription.setText(movie.getDescription());
        tvRating.setText(movie.getRating());
        tvEditor.setText(movie.getDirector());
        iv_photo.setImageResource(movie.getPhoto());

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(movie.getTitle());
        }

    }
}
