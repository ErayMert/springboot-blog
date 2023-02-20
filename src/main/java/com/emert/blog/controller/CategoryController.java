package com.emert.blog.controller;

import com.emert.blog.payload.base.BaseResponse;
import com.emert.blog.payload.dto.CategoryDto;
import com.emert.blog.payload.request.CategoryRequest;
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

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BaseResponse<Void>> addCategory(@RequestBody CategoryRequest request){
        categoryService.addCategory(request);
        return ResponseEntity.ok(new BaseResponse<>());
    }

    @GetMapping("{id}")
    public ResponseEntity<BaseResponse<CategoryDto>> getCategory(@PathVariable("id") Long categoryId){
         CategoryDto categoryDto = categoryService.getCategory(categoryId);
         return ResponseEntity.ok(new BaseResponse<>(categoryDto));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<CategoryDto>>> getCategories(){
        return ResponseEntity.ok(new BaseResponse<>(categoryService.getAllCategories()));
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