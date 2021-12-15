package com.stortor.hw7.errors;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class FieldsValidationError {
    private List<String> errorFieldsMassages;

    public FieldsValidationError(List<String> errorFieldsMassages) {
        this.errorFieldsMassages = errorFieldsMassages;
    }
}
