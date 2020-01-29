package com.zaelani.submission_moviecatalog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zaelani.submission_moviecatalog.model.MovieItems;

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

        MovieItems movieItems = getIntent().getParcelableExtra(EXTRA_MOVIE);
        tvName.setText(movieItems.getName());
        tvDescription.setText(movieItems.getDescription());
        tvRating.setText(movieItems.getRating());
        tvEditor.setText(movieItems.getDirector());

        Glide.with(this)
                .load(movieItems.getPhoto())
                .into(iv_photo);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(movieItems.getTitle());
        }

    }
}
