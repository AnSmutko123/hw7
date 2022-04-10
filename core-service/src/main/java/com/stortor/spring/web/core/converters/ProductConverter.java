package com.stortor.spring.web.core.converters;

import com.stortor.spring.web.api.core.ProductDto;
import com.stortor.spring.web.core.entity.Product;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface ProductConverter {

    ProductConverter INSTANCE = Mappers.getMapper(ProductConverter.class);

    Product dtoToEntity(ProductDto productDto);
    ProductDto entityToDto(Product product);

}
