package com.stortor.spring.web.core.validators;

import com.stortor.spring.web.api.exceptions.ValidationException;
import com.stortor.spring.web.api.core.ProductDto;
import com.stortor.spring.web.core.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ProductValidator {

    public void validate(ProductDto productDto) {
        List<String> errors = new ArrayList<>();
        if (productDto.getTitle().isBlank() || productDto.getTitle().isEmpty()) {
            errors.add("У продукта должно быть имя");
        }
        if (productDto.getPrice() < 1) {
            errors.add("Стоимость продукта должна быть не менее 1 руб");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public void validatePrice(Product product) {
        if (product.getPrice() < 1) {
            throw new ValidationException("Стоимость продукта должна быть не менее 1 руб");
        }
    }

}
