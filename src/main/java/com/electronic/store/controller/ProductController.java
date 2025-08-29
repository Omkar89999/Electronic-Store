package com.electronic.store.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.electronic.store.dtos.ApiResponseMessage;
import com.electronic.store.dtos.ImageResponse;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.ProductDto;
import com.electronic.store.services.FileService;
import com.electronic.store.services.ProductService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imageUploadPath;

    // create
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {

        ProductDto product = productService.createProduct(productDto);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,
            @PathVariable String productId) {

        ProductDto updatedProduct = productService.updateProduct(productDto, productId);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // get single product
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {

        ProductDto product = productService.getProduct(productId);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // get all product
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageableResponse<ProductDto> allProduct = productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(allProduct, HttpStatus.OK);
    }

    // delete product

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId) {

        productService.deleteProduct(productId);

        ApiResponseMessage deletedProduct = ApiResponseMessage.builder().message("Product is deleted successfully !!")
                .status(HttpStatus.OK).success(true).build();

        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }

    // get all live
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getLiveProduct(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageableResponse<ProductDto> allLiveProduct = this.productService.getAllLive(pageNumber, pageSize, sortBy,
                sortDir);

        return new ResponseEntity<>(allLiveProduct, HttpStatus.OK);
    }

    // search product by title
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(@PathVariable String query,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageableResponse<ProductDto> searchByTitleProduct = productService.searchByTitle(query, pageNumber, pageSize,
                sortBy, sortDir);

        return new ResponseEntity<>(searchByTitleProduct, HttpStatus.OK);
    }

    // upload product image
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(@PathVariable String productId,
            @RequestParam("productImage") MultipartFile image) throws IOException {
        String imageName = fileService.uploadFile(image, imageUploadPath);
        ProductDto product = productService.getProduct(productId);
        product.setProductImage(imageName);
        productService.updateProduct(product, productId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true)
                .message("image is uploaded successfully !!").status(HttpStatus.CREATED).build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    // serve product image
    @GetMapping("/image/{productId}")
    public void serveProductImage(@PathVariable String productId,HttpServletResponse response) throws IOException{

        ProductDto product = productService.getProduct(productId);
        InputStream resource = fileService.getResource(imageUploadPath, product.getProductImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());


    }

}
