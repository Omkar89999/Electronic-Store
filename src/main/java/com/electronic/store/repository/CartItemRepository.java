package com.electronic.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronic.store.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {

}
