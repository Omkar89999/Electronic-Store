package com.electronic.store.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronic.store.entity.Category;
import com.electronic.store.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository <Product, String>{
    
// search

Page<Product>findByTitleContaining(String subTitle,Pageable pageable);

Page<Product>findByLiveTrue(Pageable pageable);

Page<Product> findByCategory(Category category,Pageable pageable);

}
