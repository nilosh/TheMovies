package com.example.themovies.models;

public class Cast {
    private int id;
    private String name;
    private String character;
    private String photo;

    public Cast(int id, String name, String character, String photo) {
        this.id = id;
        this.name = name;
        this.character = character;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }

    public String getPhoto() {
        return "https://image.tmdb.org/t/p/w500/" + photo;
    }
}
