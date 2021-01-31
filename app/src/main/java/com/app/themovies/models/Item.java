package com.app.themovies.models;

public class Item {
    private final int type;
    private final Object object;

    // Constructor for generating item.
    public Item(int type, Object object) {
        this.type = type;
        this.object = object;
    }

    // Getters.
    public int getType() {
        return type;
    }

    public Object getObject() {
        return object;
    }
}
