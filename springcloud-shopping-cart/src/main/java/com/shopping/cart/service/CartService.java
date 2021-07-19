package com.shopping.cart.service;

import com.shopping.cart.dto.CartDTO;
import com.shopping.cart.dto.ItemDTO;
import com.shopping.cart.model.Cart;
import com.shopping.cart.model.Item;
import com.shopping.cart.model.Product;
import com.shopping.cart.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartService {

    private CartRepository cartRepository;

    private RestTemplate restTemplate;

    public Cart addItemService(Long id, com.shopping.cart.model.Item item) {
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

    public CartDTO findByIdService(Long id) {
        Cart cart = cartRepository.findById(id).get();

        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());

        List<ItemDTO> listItemDto = new ArrayList<>();
        cart.getItems().forEach(item -> {
            ResponseEntity<Product> prodcutCatalog = restTemplate.exchange(
                    "http://localhost:3004/api/v1/product/" + item.getProductId(),
                    HttpMethod.GET, HttpEntity.EMPTY, Product.class
            );
            listItemDto.add(new ItemDTO(prodcutCatalog.getBody().getName(), item.getAmount()));
        });
        cartDTO.setItems(listItemDto);

        return cartDTO;
    }

    public void clearCartService(Long id) {
        cartRepository.deleteById(id);
    }
}
