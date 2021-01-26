package com.example.themovies.models;

public class Cast {
    private int id;
    private String name;
    private String character;
    private String photo;

    public Cast(int id, String name, String character) {
        this.id = id;
        this.name = name;
        this.character = character;
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
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
