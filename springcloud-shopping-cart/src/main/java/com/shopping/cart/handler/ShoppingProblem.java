package com.shopping.cart.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShoppingProblem {

    private String title;
    private Integer status;
    private LocalDateTime dateTime;
    private Object productException;

}
