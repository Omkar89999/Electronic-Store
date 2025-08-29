package com.electronic.store.services;

import org.springframework.stereotype.Service;

import com.electronic.store.dtos.CategoryDto;
import com.electronic.store.dtos.PageableResponse;
@Service
public interface CategoryService {

    // create
    CategoryDto create(CategoryDto categoryDto);

    // delete
    void delete(String categoryId);

    // update
    CategoryDto update(CategoryDto categoryDto, String categoryId);

    // get all
    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    // get single category by id
    CategoryDto get(String categoryId);

    // search

}
