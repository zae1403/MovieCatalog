package com.zaelani.submission_moviecatalog.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zaelani.submission_moviecatalog.R;
import com.zaelani.submission_moviecatalog.database.MovieHelper;
import com.zaelani.submission_moviecatalog.model.MovieItems;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ListViewHolder> {
    private ArrayList<MovieItems> movies = new ArrayList<>();
    private AdapterOnClick adapterOnClick;
    private Boolean isFavorite;
    private String state;

    public void setMovies(ArrayList<MovieItems> items, AdapterOnClick adapterOnClick, Boolean isFavorite, String state) {
        movies.clear();
        movies.addAll(items);
        notifyDataSetChanged();
        this.adapterOnClick = adapterOnClick;
        this.isFavorite = isFavorite;
        this.state = state;
    }

    @NonNull
    @Override
    public ListMovieAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_movie, parent, false);
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

    private void removeItem(int position) {
        this.movies.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, movies.size());
    }


    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvRating, tvDescription;
        ImageView ivPhoto;
        ImageButton btnDelete;
        private MovieHelper movieHelper = MovieHelper.getInstance(itemView.getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());

        ListViewHolder(@NonNull View itemView) {
            super(itemView);


            tvName = itemView.findViewById(R.id.tv_name);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvDescription = itemView.findViewById(R.id.tv_description);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }

        void bind(final MovieItems movieItems) {
            tvName.setText(movieItems.getName());
            tvDescription.setText(movieItems.getDescription());
            tvRating.setText(movieItems.getRating());

            Glide.with(itemView.getContext())
                    .load(movieItems.getPhoto())
                    .apply(new RequestOptions().override(60, 90))
                    .into(ivPhoto);

            if (isFavorite) {
                btnDelete.setVisibility(View.VISIBLE);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog(movieItems);
                    }
                });

            } else {
                btnDelete.setVisibility(View.GONE);

            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterOnClick.onClick(movieItems);
                }
            });
        }

        private void alertDialog(final MovieItems movieItems){
            String dialogMessage = "Apakah anda yakin ingin menghapus item ini?";
            String dialogTitle = "Hapus Favorite";
            builder.setTitle(dialogTitle);
            builder
                    .setMessage(dialogMessage)
                    .setCancelable(false)
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            movieHelper.openMovie();
                            if (state.equals("movie")){
                                removeItem(getAdapterPosition());
                                movieHelper.deleteMovieById(movieItems.getId());
                            }else {
                                removeItem(getAdapterPosition());
                                movieHelper.deleteTvshowById(movieItems.getId());
                            }
                            Toast.makeText(itemView.getContext(), "Berhasil menghapus dari favorit", Toast.LENGTH_SHORT).show();
                            movieHelper.closeMovie();

                        }
                    })
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public interface AdapterOnClick {
        void onClick(MovieItems movieItems);
    }
}
