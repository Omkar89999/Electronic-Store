package com.electronic.store.dtos;

import com.electronic.store.entity.Cart;
import com.electronic.store.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    
    private String cartItemId;

  
    private Product product;

   
    private Cart cart;

    private int quantity;

    private int totalPrice;
}
