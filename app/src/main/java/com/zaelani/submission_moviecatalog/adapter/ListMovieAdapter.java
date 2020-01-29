package com.zaelani.submission_moviecatalog.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zaelani.submission_moviecatalog.DetailMovieActivity;
import com.zaelani.submission_moviecatalog.R;
import com.zaelani.submission_moviecatalog.model.MovieItems;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListMovieAdapter extends RecyclerView.Adapter <ListMovieAdapter.ListViewHolder> {
    private ArrayList<MovieItems> movies = new ArrayList<>();

    public void setMovies(ArrayList<MovieItems> items) {
        movies.clear();
        movies.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListMovieAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_movie,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListMovieAdapter.ListViewHolder holder, final int position) {
       holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvRating,tvDescription;
        ImageView ivPhoto;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvDescription = itemView.findViewById(R.id.tv_description);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
        }

        void bind(final MovieItems movieItems){
            tvName.setText(movieItems.getName());
            tvDescription.setText(movieItems.getDescription());
            tvRating.setText(movieItems.getRating());

            Glide.with(itemView.getContext())
                    .load(movieItems.getPhoto())
                    .apply(new RequestOptions().override(60,90))
                    .into(ivPhoto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), DetailMovieActivity.class);
                    intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieItems);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
