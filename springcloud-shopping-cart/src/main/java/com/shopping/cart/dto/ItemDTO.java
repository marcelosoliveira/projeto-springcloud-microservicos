package com.shopping.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
public class ItemDTO {

    private String name;
    private Integer amount;

}
