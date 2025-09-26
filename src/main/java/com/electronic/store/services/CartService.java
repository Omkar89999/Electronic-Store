package com.electronic.store.services;

import com.electronic.store.dtos.AddItemToCartRequest;
import com.electronic.store.dtos.CartDto;

public interface CartService {


    // add item to cart
    CartDto addItemToCart(String userId,AddItemToCartRequest request);

    // remove item from cart
    void removeItemFromCart(String userId,String cartItemId);

    // clear cart
    void clearCart(String userId);

    // // get cart by user
    // CartDto getCartByUser(String userId);

    // // delete cart
    // void deleteCart(String userId);

    // // get total cart of user
    // int getTotalCartOfUser(String userId);


    
    
}
