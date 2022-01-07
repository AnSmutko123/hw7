package com.stortor.hw7.dto;
import com.stortor.hw7.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private Collection<Role> roles;

    public UserDto(String username, String name, String email) {
        this.username = username;
        this.name = name;
        this.email = email;
    }

    public UserDto(String username, String name, String email, Collection<Role> roles) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }
}
