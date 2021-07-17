package com.shopping.cart.repository;

import com.shopping.cart.model.Cart;
import com.shopping.cart.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
}
