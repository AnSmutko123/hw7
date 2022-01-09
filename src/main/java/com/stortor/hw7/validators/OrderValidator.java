package com.stortor.hw7.validators;

import com.stortor.hw7.dto.OrderDto;
import com.stortor.hw7.dto.OrderItemDto;
import com.stortor.hw7.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class OrderValidator {

    private final List<String> errors = new ArrayList<>();

//    public void validate(OrderDto orderDto) {
//        if (orderDto.getOrderItemsDto().isEmpty()) {
//            errors.add("Список продуктов для покупки не может быть пустым");
//        }
//        if (!orderDto.getOrderItemsDto().isEmpty()) {
//            validateOrderItems(orderDto);
//        }
//        if (orderDto.getTotalPrice() < 1) {
//            errors.add("Стоимость товаров должна быть не менее 1 руб");
//        }
//        if (orderDto.getPhone().isEmpty() || orderDto.getPhone().isBlank()) {
//            errors.add("Контактный телефон должен быть указан");
//        }
//        if (!errors.isEmpty()) {
//            throw new ValidationException(errors);
//        }
//    }
//
//    private void validateOrderItems(OrderDto orderDto) {
//        List<OrderItemDto> orderItemDtos = orderDto.getOrderItemsDto();
//        if (orderItemDtos.isEmpty()) {
//            errors.add("Список продуктов для покупки не может быть пустым");
//        }
//        if (orderItemDtos.stream().map(p -> p.getQuantity()).anyMatch(q -> q < 1)) {
//            errors.add("Количество товаров не может быть меньше 1");
//        }
//        if (orderItemDtos.stream().map(p -> p.getPricePerProduct()).anyMatch(q -> q < 1)) {
//            errors.add("Стоимость товара не может быть меньше 1");
//        }
//        if (orderItemDtos.stream().map(p -> p.getPrice()).anyMatch(q -> q < 1)) {
//            errors.add("Общая стоимость товара не может быть меньше 1");
//        }
//        if (orderItemDtos.stream().map(p -> p.getProductTitle()).anyMatch(q -> q.isEmpty() || q.isBlank())) {
//            errors.add("У товара должно быть название");
//        }
//    }

}
