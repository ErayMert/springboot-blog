package com.emert.blog.mapper;

import com.emert.blog.entity.Category;
import com.emert.blog.payload.dto.CategoryDto;
import com.emert.blog.payload.request.CategoryRequest;
import com.emert.blog.payload.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CategoryMapper {
    Category categoryRequestToCategory(CategoryRequest request);
    CategoryDto categoryToCategoryDto(Category category);
    CategoryResponse categoryDtoToCategoryResponse(CategoryDto category);
    List<CategoryDto> categoryListToCategoryDtoList(List<Category> categories);
    List<CategoryResponse> categoryDtoListToCategoryResponseList(List<CategoryDto> categories);

}
