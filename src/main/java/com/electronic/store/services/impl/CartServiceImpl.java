package com.electronic.store.services.impl;

import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electronic.store.dtos.AddItemToCartRequest;
import com.electronic.store.dtos.CartDto;
import com.electronic.store.entity.Cart;
import com.electronic.store.entity.Product;
import com.electronic.store.entity.User;
import com.electronic.store.repository.CartRepository;
import com.electronic.store.repository.ProductRepository;
import com.electronic.store.repository.UserRepository;
import com.electronic.store.services.CartService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        int quantity = request.getQuantity();
        String productId = request.getProductId();
        // fetch the product from product id
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with given id !!"));

        // fetch the user from user id
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with given id !!"));

                Cart cart = null;

                try {
                    cart = cartRepository.findByUser(user).get();

                } catch (NoSuchElementException e) {
                  
                }
        return null;
    }

    @Override
    public void clearCart(String userId) {

    }

    @Override
    public void removeItemFromCart(String userId, String cartItemId) {

    }

}
