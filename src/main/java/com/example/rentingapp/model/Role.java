package com.example.project.model;

public enum Role {
    ADMIN, USER, MANAGER;

    public String getName() {
        return name().toLowerCase();
    }
}
