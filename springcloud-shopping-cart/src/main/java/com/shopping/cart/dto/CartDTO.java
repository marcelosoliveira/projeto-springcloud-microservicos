package com.shopping.cart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
public class CartDTO {

    @Id
    private Long id;
    private List<ItemDTO> items;

}
