package com.electronic.store.services.impl;

import org.springframework.stereotype.Service;

import com.electronic.store.dtos.AddItemToCartRequest;
import com.electronic.store.dtos.CartDto;
import com.electronic.store.services.CartService;
@Service
public class CartServiceImpl implements CartService{

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
      



        return null;
    }

    @Override
    public void clearCart(String userId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeItemFromCart(String userId, String cartItemId) {
        // TODO Auto-generated method stub
        
    }
    
}
