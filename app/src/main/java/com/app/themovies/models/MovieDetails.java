package com.app.themovies.models;

import java.util.ArrayList;

public class MovieDetails {
    private final int id;
    private final String title;
    private final String tagline;
    private final String year;
    ArrayList<String> genres;
    private final String runtime;
    private final String description;
    private final String voteAverage;
    private final String voteCount;
    ArrayList<Cast> cast;
    private final String poster;
    private final String backdrop;

    public MovieDetails(int id, String title, String tagline, String year, ArrayList<String> genres,
                        String runtime, String description, String voteAverage, String voteCount,
                        String poster, String backdrop) {
        this.id = id;
        this.title = title;
        this.tagline = tagline;
        this.year = year;
        this.genres = genres;
        this.runtime = runtime;
        this.description = description;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.poster = poster;
        this.backdrop = backdrop;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTagline() {
        return tagline;
    }

    public String getYear() {

        return year.substring(0, 4);
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public String getRuntime() {
        int hours = Integer.parseInt(runtime) / 60;
        int minutes = Integer.parseInt(runtime) % 60;

        return hours + " Hrs and " + Math.round(minutes) + " Mins";
    }

    public String getDescription() {
        return description;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getPoster() {
        return poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public ArrayList<Cast> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Cast> cast) {
        this.cast = cast;
    }
}
