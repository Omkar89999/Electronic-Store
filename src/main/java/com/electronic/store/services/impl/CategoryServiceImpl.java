package com.electronic.store.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.electronic.store.dtos.CategoryDto;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.entity.Category;

import com.electronic.store.exceptions.ResourceNotFoundException;
import com.electronic.store.helper.Helper;
import com.electronic.store.repository.CategoryRepository;
import com.electronic.store.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private ModelMapper mapper;

    @Value("${category.profile.image.path}")
    private String imagePath;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {

        // creating category id randomly
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);

        Category category = mapper.map(categoryDto, Category.class);

        Category savedCategory = categoryRepository.save(category);

        return mapper.map(savedCategory, CategoryDto.class);
    }

    // delete category and category cover Image
    @Override
    public void delete(String categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with given id !!"));

        // delete category profile image
        String fullPath = imagePath + category.getCoverImage();
        Path path = Paths.get(fullPath);

        try {
            Files.delete(path);
        } catch (NoSuchFileException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        categoryRepository.delete(category);

    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {

        // get category by id
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with given id !!"));

        // update category details

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(category);
        return mapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);

        return pageableResponse;
    }

    @Override
    public CategoryDto get(String categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with given id !!"));

        return mapper.map(category, CategoryDto.class);
    }

}
