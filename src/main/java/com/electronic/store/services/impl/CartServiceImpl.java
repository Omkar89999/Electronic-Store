package com.electronic.store.services.impl;

import java.sql.Date;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electronic.store.dtos.AddItemToCartRequest;
import com.electronic.store.dtos.CartDto;
import com.electronic.store.entity.Cart;
import com.electronic.store.entity.CartItem;
import com.electronic.store.entity.Product;
import com.electronic.store.entity.User;
import com.electronic.store.repository.CartItemRepository;
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
    private CartItemRepository cartItemRepository;

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
            cart = new Cart();

            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date(System.currentTimeMillis()));
        }

        // perform cart operations
        // if cart already present then update it.

        // boolean updated = false;
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        
        List<CartItem> updatedItems = items.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
                // item already present in cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getPrice());

                // updated = true;
                updated.set(true);
            }
            return item;

        }).collect(Collectors.toList());

        cart.setItems(updatedItems);

        if (!updated.get()) {
            // item not present in cart
            // create a new cart item
            CartItem cartItem = CartItem.builder().quantity(quantity).totalPrice(quantity * product.getPrice())
                    .cart(cart)
                    .product(product).build();

            cart.getItems().add(cartItem);

        }

        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);

        return mapper.map(updatedCart, CartDto.class);
    }

    @Override
    public void clearCart(String userId) {

        

    }

    @Override
    public void removeItemFromCart(String userId, String cartItemId) {

    }

}
