package com.stortor.spring.web.api.exceptions;

import java.util.List;


public class FieldsValidationError {
    private List<String> errorFieldsMassages;

    public FieldsValidationError(List<String> errorFieldsMassages) {
        this.errorFieldsMassages = errorFieldsMassages;
    }

    public FieldsValidationError() {
    }

    public List<String> getErrorFieldsMassages() {
        return errorFieldsMassages;
    }

    public void setErrorFieldsMassages(List<String> errorFieldsMassages) {
        this.errorFieldsMassages = errorFieldsMassages;
    }
}
