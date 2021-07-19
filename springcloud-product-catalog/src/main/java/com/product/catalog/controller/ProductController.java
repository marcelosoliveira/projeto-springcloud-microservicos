package com.product.catalog.controller;

import com.product.catalog.dto.request.ProductDTO;
import com.product.catalog.dto.response.MessageResponseDTO;
import com.product.catalog.service.impl.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private ProductService productService;

    @PostMapping("/product")
    public MessageResponseDTO createProductController(@Valid @RequestBody ProductDTO productDTO) {
        return productService.createProductService(productDTO);
    }

    @GetMapping("/product")
    public List<ProductDTO> listProductController() {
        return productService.listProductService();
    }

    @GetMapping("/product/{id}")
    public ProductDTO getByProductId(@PathVariable Long id) {
        return productService.findByProductId(id);
    }
}
