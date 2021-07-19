package com.shopping.cart.service.impl;

import com.shopping.cart.dto.CartDTO;
import com.shopping.cart.dto.ItemDTO;
import com.shopping.cart.exception.ShoppingCartNotFoundException;
import com.shopping.cart.model.Cart;
import com.shopping.cart.model.Item;
import com.shopping.cart.model.Product;
import com.shopping.cart.repository.CartRepository;
import com.shopping.cart.service.interfaces.CartInterface;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService implements CartInterface {

    private CartRepository cartRepository;

    private RestTemplate restTemplate;

    public ResponseEntity<Object> addItemService(Long id, Item item) {
        ResponseEntity<Exception> productCatalog = restTemplate.exchange(
                "http://localhost:3004/api/v1/product/" + item.getProductId(),
                HttpMethod.GET, HttpEntity.EMPTY, Exception.class
        );

        Cart cart;
        Optional<Cart> savedCart = cartRepository.findById(id);

        if (savedCart.isPresent()) {
            cart = savedCart.get();
        }
        else {
            cart = new Cart(id);
        }
        cart.getItems().add(item);
        Cart cartSaved = cartRepository.save(cart);
        return ResponseEntity.ok().body(cartSaved);
    }

    public CartDTO findByIdService(Long id) {
        verifyCartExists(id);
        Cart cart = cartRepository.findById(id).get();

        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());

        List<ItemDTO> listItemDto = new ArrayList<>();
        cart.getItems().forEach(item -> {
            ResponseEntity<Product> productCatalog = restTemplate.exchange(
                    "http://localhost:3004/api/v1/product/" + item.getProductId(),
                    HttpMethod.GET, HttpEntity.EMPTY, Product.class
            );
            listItemDto.add(new ItemDTO(productCatalog.getBody().getName(), item.getAmount()));
        });
        cartDTO.setItems(listItemDto);

        return cartDTO;
    }

    public void clearCartService(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public void verifyCartExists(Long id) {
        this.cartRepository.findById(id).orElseThrow(
                () -> new ShoppingCartNotFoundException("Carrinho de compra não existe!")
        );
    }
}
