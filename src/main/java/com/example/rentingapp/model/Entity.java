package com.example.rentingapp.model;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
