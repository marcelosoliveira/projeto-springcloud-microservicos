package com.product.catalog.service.impl;

import com.product.catalog.dto.request.ProductDTO;
import com.product.catalog.dto.response.MessageResponseDTO;
import com.product.catalog.exception.ProductNotFoundException;
import com.product.catalog.mapper.ProductMapper;
import com.product.catalog.model.Product;
import com.product.catalog.repository.ProductRepository;
import com.product.catalog.service.interfaces.ProductInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService implements ProductInterface {

    private ProductRepository productRepository;

    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    public MessageResponseDTO createProductService(ProductDTO productDTO) {
        Product product = productMapper.toProductModel(productDTO);
        this.productRepository.save(product);
        return createMessageResponse("Successfully created product!");
    }

    public List<ProductDTO> listProductService() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO findByProductId(Long id) {
        verifyExistProduct(id);
        Product product = productRepository.findById(id).get();
        return productMapper.toProductDTO(product);
    }

    private MessageResponseDTO createMessageResponse(String message) {
        return MessageResponseDTO
                .builder()
                .message(message)
                .build();
    }


    @Override
    public void verifyExistProduct(Long id) {
        this.productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Produto não encontrado!")
        );
    }
}
