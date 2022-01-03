package com.stortor.hw7.controllers;

import com.stortor.hw7.configs.SecurityConfig;
import com.stortor.hw7.converters.UserConverter;
import com.stortor.hw7.dto.JwtRequest;
import com.stortor.hw7.dto.JwtResponse;
import com.stortor.hw7.dto.RegisterUserDto;
import com.stortor.hw7.errors.AppError;
import com.stortor.hw7.servieces.UserService;
import com.stortor.hw7.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    private final UserService userService;
    private final UserConverter userConverter;
    private final SecurityConfig securityConfig;

    @PostMapping("/register")
    public RegisterUserDto registerNewUser(@RequestBody RegisterUserDto userDto) {
        userDto.setId(null);
        String pass = securityConfig.passwordEncoder().encode(userDto.getPassword());
        userDto.setPassword(pass);
        return userConverter.entityToDto(userService.save(userConverter.dtoToEntity(userDto)));
    }

}
