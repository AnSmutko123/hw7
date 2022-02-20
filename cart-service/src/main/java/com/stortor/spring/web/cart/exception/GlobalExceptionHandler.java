package com.stortor.spring.web.cart.exception;

import com.stortor.spring.web.api.errors.AppError;
import com.stortor.spring.web.api.errors.ServerNotWorkingError;
import com.stortor.spring.web.api.exceptions.FieldsValidationError;
import com.stortor.spring.web.api.exceptions.ResourceNotFoundException;
import com.stortor.spring.web.api.exceptions.ServerNotWorkingException;
import com.stortor.spring.web.api.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<FieldsValidationError> catchValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new FieldsValidationError(e.getErrorFieldsMessages()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ServerNotWorkingError> catchServerNotWorkingException(ServerNotWorkingException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ServerNotWorkingError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
