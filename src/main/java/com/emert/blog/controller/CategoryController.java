package com.emert.blog.controller;

import com.emert.blog.mapper.CategoryMapper;
import com.emert.blog.payload.base.BaseResponse;
import com.emert.blog.payload.request.CategoryRequest;
import com.emert.blog.payload.response.CategoryResponse;
import com.emert.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BaseResponse<Void>> saveCategory(@RequestBody CategoryRequest request){
        categoryService.saveCategory(request);
        return new ResponseEntity<>(new BaseResponse<>(), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<BaseResponse<CategoryResponse>> getCategory(@PathVariable("id") Long categoryId){
         CategoryResponse category = categoryMapper.categoryDtoToCategoryResponse(categoryService.getCategory(categoryId));
         return ResponseEntity.ok(new BaseResponse<>(category));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<CategoryResponse>>> getCategories(){
        return ResponseEntity.ok(
                new BaseResponse<>(
                categoryMapper.categoryDtoListToCategoryResponseList(categoryService.getAllCategories())
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<BaseResponse<Void>> updateCategory(@RequestBody CategoryRequest request,
                                                      @PathVariable("id") Long categoryId){
        categoryService.updateCategory(request, categoryId);
        return ResponseEntity.ok(new BaseResponse<>());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<BaseResponse<Void>> deleteCategory(@PathVariable("id") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(new BaseResponse<>());
    }
}