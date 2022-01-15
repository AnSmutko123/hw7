package com.stortor.hw7.validators;

import com.stortor.hw7.dto.ProductDto;
import com.stortor.hw7.entity.Product;
import com.stortor.hw7.exceptions.ValidationException;
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
