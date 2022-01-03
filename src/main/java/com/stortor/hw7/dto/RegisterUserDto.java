package com.stortor.hw7.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;

    public RegisterUserDto(String username, String name, String email) {
        this.username = username;
        this.name = name;
        this.email = email;
    }
}
