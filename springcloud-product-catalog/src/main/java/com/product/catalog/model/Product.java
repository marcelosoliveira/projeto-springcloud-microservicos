package com.product.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Builder
@Document(indexName = "product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private Long id;
    private String name;
    private Integer amount;

}
