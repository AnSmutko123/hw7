package com.stortor.spring.web.api.core;


public class ProfileDto {
    private String username;

    public ProfileDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public ProfileDto() {
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
