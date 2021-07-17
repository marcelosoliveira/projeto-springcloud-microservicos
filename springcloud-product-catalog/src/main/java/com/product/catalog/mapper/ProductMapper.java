package com.product.catalog.mapper;

import com.product.catalog.dto.request.ProductDTO;
import com.product.catalog.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toProductModel(ProductDTO productDTO);

    ProductDTO toProductDTO(Product product);
}
