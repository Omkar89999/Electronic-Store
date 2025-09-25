package com.electronic.store.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
    
    @Id
    private String cartId;

    @OneToOne
    private User user;

    private Date createdAt;

    // mapping cart items
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "cart")
    private List<CartItem> items=new ArrayList<>();
}
