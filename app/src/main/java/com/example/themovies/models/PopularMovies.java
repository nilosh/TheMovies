package com.example.themovies.models;

import android.widget.ImageView;

public class PopularMovies {

    // Variables to hold the values of views.
    private int movieId;
    private String moviePoster, movieTitle, movieReleaseDate, movieRating;

    // Constructor for creating popular movies.
    public PopularMovies(int movieId, String moviePoster, String movieTitle, String movieReleaseDate,
                         String movieRating) {
        this.movieId = movieId;
        this.moviePoster = moviePoster;
        this.movieTitle = movieTitle;
        this.movieReleaseDate = movieReleaseDate;
        this.movieRating = movieRating;
    }

    // Generate setters and getters.
    public int getMovieId() {
        return movieId;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public String getMovieRating() {
        return movieRating;
    }
}
