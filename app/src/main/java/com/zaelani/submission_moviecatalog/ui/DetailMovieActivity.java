package com.zaelani.submission_moviecatalog.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.zaelani.submission_moviecatalog.R;
import com.zaelani.submission_moviecatalog.database.MovieHelper;
import com.zaelani.submission_moviecatalog.model.MovieItems;


public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_STATE = "extra_state";
    public static final String EXTRA_TITLE = "extra_title";
    private MovieHelper movieHelper;
    private MovieItems movieItems = new MovieItems();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tvName = findViewById(R.id.tv_detail_name);
        TextView tvRating = findViewById(R.id.tv_detail_rating);
        TextView tvDescription = findViewById(R.id.tv_detail_description);
        TextView tvEditor = findViewById(R.id.tv_detail_editor);
        TextView tvToolbar = toolbar.findViewById(R.id.tv_title_toolbar);
        ImageView iv_photo = findViewById(R.id.img_photo);

        movieHelper = MovieHelper.getInstance(getApplicationContext());

        MovieItems movieItems = getIntent().getParcelableExtra(EXTRA_MOVIE);
        tvName.setText(movieItems.getName());
        tvDescription.setText(movieItems.getDescription());
        tvRating.setText(movieItems.getRating());
        tvEditor.setText(movieItems.getDirector());

        Glide.with(this)
                .load(movieItems.getPhoto())
                .into(iv_photo);

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            tvToolbar.setText(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);

        MenuItem item = menu.findItem(R.id.favorite_menu);
        item.setActionView(R.layout.button_favorite);
        ToggleButton button = item.getActionView().findViewById(R.id.btn_favorite);
        button.setChecked(false);


        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                movieHelper.openMovie();
                movieItems = getIntent().getParcelableExtra(EXTRA_MOVIE);
                String tag = getIntent().getStringExtra(EXTRA_STATE);

                if (b) {
                    if (tag.equals("movies")) {
                        if (movieHelper.selectMovieId(movieItems.getId()) == 0){
                            movieHelper.insertMovie(movieItems);
                            Toast.makeText(getApplicationContext(), getString(R.string.result_succes), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), getString(R.string.does_exist), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        if (movieHelper.selectTvshowId(movieItems.getId())== 0){
                            movieHelper.insertTvshow(movieItems);
                            Toast.makeText(getApplicationContext(), getString(R.string.result_succes), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), getString(R.string.does_exist), Toast.LENGTH_SHORT).show();
                        }
                    }
                    movieHelper.closeMovie();

                }
            }
        });
        return true;
    }
}
