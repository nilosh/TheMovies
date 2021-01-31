package com.app.themovies.models;

public class PopularMovies {

    // Variables to hold the values of views.
    private final int movieId;
    private final String moviePoster;
    private final String movieTitle;
    private final String movieReleaseDate;
    private final String movieRating;

    // Constructor for creating popular movies.
    public PopularMovies(int movieId, String moviePoster, String movieTitle,
                         String movieReleaseDate, String movieRating) {
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
        return movieReleaseDate.substring(0, 4);
    }

    public String getMovieRating() {
        return movieRating;
    }
}
