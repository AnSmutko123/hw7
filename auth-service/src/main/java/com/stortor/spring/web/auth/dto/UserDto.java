package com.stortor.spring.web.auth.dto;
import com.stortor.spring.web.auth.entity.Role;
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

    public UserDto(String username, String email, Collection<Role> roles) {
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public UserDto(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
