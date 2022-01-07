package com.stortor.hw7.controllers;

import com.stortor.hw7.converters.UserConverter;
import com.stortor.hw7.dto.JwtRequest;
import com.stortor.hw7.dto.JwtResponse;
import com.stortor.hw7.dto.UserDto;
import com.stortor.hw7.entity.Role;
import com.stortor.hw7.errors.AppError;
import com.stortor.hw7.servieces.UserService;
import com.stortor.hw7.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserConverter userConverter;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

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
