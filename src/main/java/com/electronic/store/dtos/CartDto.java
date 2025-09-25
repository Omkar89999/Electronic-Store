package com.electronic.store.dtos;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.electronic.store.entity.CartItem;
import com.electronic.store.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private String cartId;

    private User user;

    private Date createdAt;

    // mapping cart items

    private List<CartItem> items = new ArrayList<>();

}
