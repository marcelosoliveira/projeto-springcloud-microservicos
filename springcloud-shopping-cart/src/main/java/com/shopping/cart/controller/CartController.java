package com.shopping.cart.controller;

import com.shopping.cart.dto.CartDTO;
import com.shopping.cart.model.Cart;
import com.shopping.cart.model.Item;
import com.shopping.cart.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class CartController {

    private CartService cartService;

    @PostMapping("cart/{id}")
    public Cart addItemController(@PathVariable Long id, @RequestBody Item item) {
        return cartService.addItemService(id, item);
    }

    @GetMapping("cart/{id}")
    public CartDTO findByIdController(@PathVariable Long id) {
        return cartService.findByIdService(id);
    }

    @DeleteMapping("cart/{id}")
    public ResponseEntity<?> clearCartController(@PathVariable Long id) {
        cartService.clearCartService(id);
        return ResponseEntity.status(204).body("");
    }
}
