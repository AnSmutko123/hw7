package com.stortor.spring.web.auth.converters;

import com.stortor.spring.web.auth.dto.UserDto;
import com.stortor.spring.web.auth.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserConverter {

    public User dtoToEntity(UserDto userDto) {
        User user = new User(userDto.getId(), userDto.getName(), userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
        return user;
    }

    public UserDto entityToDto(User user) {
        UserDto userDto = new UserDto(user.getUsername(), user.getEmail());
        return userDto;
    }

    public UserDto entityToDtoWithRoles(User user) {
        UserDto userDto = new UserDto(user.getUsername(), user.getEmail(), user.getRoles());
        return userDto;
    }


}
