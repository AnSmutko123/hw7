package com.stortor.spring.web.auth.controllers;

import com.stortor.spring.web.auth.converters.UserConverter;
import com.stortor.spring.web.auth.dto.UserDto;
import com.stortor.spring.web.auth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserConverter userConverter;

        @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody UserDto userDto) {
        userDto.setId(null);
        userService.createNewUser(userConverter.dtoToEntity(userDto));
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAllUsers().stream().map(userConverter::entityToDto).collect(Collectors.toList());
    }

    @DeleteMapping("/id")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    @PatchMapping("/roles")
    public ResponseEntity<?> updateRoles(@RequestBody UserDto userDto) {
        userService.updateRoles(userDto.getId(), userDto.getRoles());
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

}
