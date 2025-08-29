// package com.electronic.store.helper;

// import org.springframework.hateoas.EntityModel;

// import org.springframework.hateoas.server.RepresentationModelAssembler;
// import org.springframework.stereotype.Component;

// import com.electronic.store.controller.CategoryController;
// import com.electronic.store.dtos.CategoryDto;
// import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
// @Component
// public class CategoryModelAssembler implements RepresentationModelAssembler<CategoryDto, EntityModel<CategoryDto>>{

//     @Override
//     public EntityModel<CategoryDto> toModel(CategoryDto categoryDto) {
    
//         return EntityModel.of(categoryDto,
//             linkTo(methodOn(CategoryController.class).getCategory(categoryDto.getCategoryId())).withSelfRel(),
//             linkTo(methodOn(CategoryController.class).getAllCategory(0, 10, "title", "asc")).withRel("categories"),
//             linkTo(methodOn(CategoryController.class).updateCategory(categoryDto.getCategoryId(), categoryDto)).withRel("update"),
//             linkTo(methodOn(CategoryController.class).deleteCategory(categoryDto.getCategoryId())).withRel("delete")
//         );
//     }

   
// }
