package com.stortor.hw7.validators;

import com.stortor.hw7.entity.User;
import com.stortor.hw7.exceptions.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterUserValidator {

    public void validate(User user, boolean userNameIsEmpty, boolean emailIsEmpty) {
        List<String> errors = new ArrayList<>();
        if (user.getUsername().isBlank() || user.getUsername().isEmpty()) {
            errors.add("У пользователя должно быть имя");
        }
        if (!userNameIsEmpty) {
            errors.add("Пользователь с данным именем уже существует");
        }
        if (user.getPassword().isBlank() || user.getPassword().isEmpty() ) {
            errors.add("У пользователя должен быть пароль");
        }
        if (user.getEmail().isBlank() || user.getEmail().isEmpty() ) {
            errors.add("Email поле обязательно к заполнению");
        }
        if (!emailIsEmpty) {
            errors.add("Пользователь с данным email уже существует");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
