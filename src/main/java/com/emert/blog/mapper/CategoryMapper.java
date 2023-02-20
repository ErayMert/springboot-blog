package com.emert.blog.mapper;

import com.emert.blog.entity.Category;
import com.emert.blog.payload.dto.CategoryDto;
import com.emert.blog.payload.request.CategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CategoryMapper {
    Category categoryRequestToCategory(CategoryRequest request);
    CategoryDto categoryToCategoryDto(Category category);
    List<CategoryDto> categoriesToCategoryDtos(List<Category> categories);

}
