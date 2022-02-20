package com.stortor.spring.web.api.errors;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель ошибки")
public class AppError {

    @Schema(description = "Код ошибки", required = true)
    private String code;
    @Schema(description = "Сообщение ошибки", required = true)
    private String message;

    public AppError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public AppError() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
