package com.electronic.store.services;

import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.ProductDto;


public interface ProductService  {

    // create product
    ProductDto createProduct(ProductDto productDto);

    // update product
    ProductDto updateProduct(ProductDto productDto, String productId); 

    // get all product
    PageableResponse<ProductDto> getAllProduct(int pageNumber,int pageSize,String sortBy,String sortDir);

    // get single product by id
    ProductDto getProduct(String productId);

    // delete product
    void deleteProduct(String productId);

    // get all live
    PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir);

    // search product
    PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir);
    

    // create product with category
    ProductDto createProductWithCategory(ProductDto productDto,String categoryId);

    // update category of product
    ProductDto updateCategory(String productId,String categoryId);

    PageableResponse<ProductDto>getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir);


}
