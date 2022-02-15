package com.stortor.spring.web.api.errors;

public class ServerNotWorkingError {
    private int statusCode;
    private String message;

    public ServerNotWorkingError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ServerNotWorkingError() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
