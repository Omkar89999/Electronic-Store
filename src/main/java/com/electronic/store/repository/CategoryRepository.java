package com.electronic.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronic.store.entity.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category,String>{
    
}
