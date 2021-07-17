package com.shopping.cart.service;

import com.shopping.cart.model.Cart;
import com.shopping.cart.model.Item;
import com.shopping.cart.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {

    private CartRepository cartRepository;

    public Cart addItemService(Long id, Item item) {
        Optional<Cart> savedCart = cartRepository.findById(id);
        Cart cart;
        if (savedCart.isPresent()) {
            cart = savedCart.get();
        }
        else {
            cart = new Cart(id);
        }
        cart.getItems().add(item);
        return cartRepository.save(cart);
    }

    public Cart findByIdService(Long id) {
        Cart cart = cartRepository.findById(id).get();
        return cart;
    }

    public void clearCartService(Long id) {
        cartRepository.deleteById(id);
    }
}
