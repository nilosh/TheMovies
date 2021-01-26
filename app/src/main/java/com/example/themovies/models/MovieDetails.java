package com.example.themovies.models;

import java.util.ArrayList;

public class MovieDetails {
    private int id;
    private String title;
    private String overview;
    private String tagline;
    private String releaseDate;
    private int runtime;
    private String status;
    private ArrayList<String> genres;
    private ArrayList<Object> productionCompanies;
    private String backdrop;
    private String poster;

    public MovieDetails(int id, String title, String releaseDate) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<Object> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(ArrayList<Object> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
