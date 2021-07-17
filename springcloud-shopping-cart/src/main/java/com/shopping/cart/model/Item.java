package com.shopping.cart.model;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("item")
public class Item {

    private Long productId;
    private Integer amount;

}
