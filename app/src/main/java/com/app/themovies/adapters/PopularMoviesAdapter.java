package com.app.themovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.themovies.R;
import com.app.themovies.models.Item;
import com.app.themovies.models.PopularMovies;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularMoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Item> items;
    private OnMovieItemClickListener movieItemClickListener;

    // Create interface to detect on movie item click.
    public interface OnMovieItemClickListener {
        void onMovieItemClick(int position);
    }

    // Method passes the activity as the listener.
    public void setOnMovieItemClickListener(OnMovieItemClickListener listener) {
        movieItemClickListener = listener;
    }

    public PopularMoviesAdapter(List<Item> items) {
        this.items = items;
    }

    // Methods for ViewHolder.
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            return new PopularLargeViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.large_cell,
                            parent,
                            false
                    ));
        } else {
            return new PopularSmallViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.small_cell,
                            parent,
                            false
                    )
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == 0) {
            PopularMovies movie = (PopularMovies) items.get(position).getObject();
            ((PopularLargeViewHolder) holder).setLgMovieDetails(movie);
        } else {
            PopularMovies movie = (PopularMovies) items.get(position).getObject();
            ((PopularSmallViewHolder) holder).setSmMovieDetails(movie);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    public class PopularLargeViewHolder extends RecyclerView.ViewHolder {
        private final ImageView lgMoviePoster;
        private final TextView lgMovieTitle;
        private final TextView lgMovieReleaseDate;
        private final TextView lgMovieRating;

        PopularLargeViewHolder(@NonNull View itemView) {
            super(itemView);
            lgMoviePoster = itemView.findViewById(R.id.lgPopMoviePoster);
            lgMovieTitle = itemView.findViewById(R.id.lgPopMovieTitle);
            lgMovieReleaseDate = itemView.findViewById(R.id.lgPopMovieReleaseDate);
            lgMovieRating = itemView.findViewById(R.id.lgRatingText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check if the listener for this interface is not null.
                    if (movieItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            movieItemClickListener.onMovieItemClick(position);
                        }
                    }
                }
            });
        }

        // Set data to views.
        void setLgMovieDetails(PopularMovies movie) {
            Picasso.get()
                    .load(movie.getMoviePoster())
                    .into(lgMoviePoster);
            lgMovieTitle.setText(movie.getMovieTitle());
            lgMovieReleaseDate.setText(movie.getMovieReleaseDate());
            lgMovieRating.setText(movie.getMovieRating());
        }
    }

    public class PopularSmallViewHolder extends RecyclerView.ViewHolder {
        private final ImageView smMoviePoster;
        private final TextView smMovieTitle;
        private final TextView smMovieReleaseDate;
        private final TextView smMovieRating;

        PopularSmallViewHolder(@NonNull View itemView) {
            super(itemView);
            smMoviePoster = itemView.findViewById(R.id.smPopMoviePoster);
            smMovieTitle = itemView.findViewById(R.id.smPopMovieTitle);
            smMovieReleaseDate = itemView.findViewById(R.id.smPopMovieReleaseDate);
            smMovieRating = itemView.findViewById(R.id.smRatingText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check if the listener for this interface is not null.
                    if (movieItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            movieItemClickListener.onMovieItemClick(position);
                        }
                    }
                }
            });
        }

        // Set data to views.
        void setSmMovieDetails(PopularMovies movie) {
            Picasso.get()
                    .load(movie.getMoviePoster())
                    .into(smMoviePoster);
            smMovieTitle.setText(movie.getMovieTitle());
            smMovieReleaseDate.setText(movie.getMovieReleaseDate());
            smMovieRating.setText(movie.getMovieRating());
        }
    }
}
