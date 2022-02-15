package com.stortor.spring.web.api.exceptions;

public class ServerNotWorkingException extends RuntimeException{
    public ServerNotWorkingException(String message) {
        super(message);
    }
}
