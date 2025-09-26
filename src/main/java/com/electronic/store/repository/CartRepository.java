package com.electronic.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronic.store.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

}
