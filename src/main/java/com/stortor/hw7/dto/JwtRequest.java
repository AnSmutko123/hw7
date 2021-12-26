package com.stortor.hw7.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
