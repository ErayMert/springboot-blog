package com.emert.blog.service;

import com.emert.blog.entity.Category;
import com.emert.blog.exception.ResourceNotFoundException;
import com.emert.blog.mapper.CategoryMapper;
import com.emert.blog.payload.dto.CategoryDto;
import com.emert.blog.payload.request.CategoryRequest;
import com.emert.blog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService{

    public static final String CATEGORY = "Category";
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public void addCategory(CategoryRequest categoryRequest) {

        Category category = categoryMapper.categoryRequestToCategory(categoryRequest);
        categoryRepository.save(category);
    }

    public CategoryDto getCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY, "id", categoryId));

        return categoryMapper.categoryToCategoryDto(category);
    }

    public List<CategoryDto> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.categoriesToCategoryDtos(categories);
    }

    public void updateCategory(CategoryRequest request, Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY, "id", categoryId));

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setId(categoryId);

        categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY, "id", categoryId));

        categoryRepository.delete(category);
    }
}
