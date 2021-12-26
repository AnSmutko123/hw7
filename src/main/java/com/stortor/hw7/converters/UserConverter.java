package com.stortor.hw7.converters;

import com.stortor.hw7.dto.RegisterUserDto;
import com.stortor.hw7.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserConverter {

    public User dtoToEntity(RegisterUserDto userDto) {
        User user = new User(userDto.getId(), userDto.getUsername(), userDto.getPassword(), userDto.getName(), userDto.getEmail());
        log.info(user.toString());
        return user;
    }

    public RegisterUserDto entityToDto(User user) {
        RegisterUserDto userDto = new RegisterUserDto(user.getUsername(), user.getName(), user.getEmail());
        return userDto;
    }


}
