package com.emert.blog.controller;

import com.emert.blog.constant.BlogConstant;
import com.emert.blog.payload.base.BaseResponse;
import com.emert.blog.payload.dto.PaginatedPostDto;
import com.emert.blog.payload.dto.PostDto;
import com.emert.blog.payload.request.PostRequest;
import com.emert.blog.payload.request.PostUpdateRequest;
import com.emert.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{categoryId}")
    public ResponseEntity<BaseResponse<Void>> createPost(@PathVariable Long categoryId, @Valid @RequestBody PostRequest postRequest){
        postService.createPost(categoryId, postRequest);
        return new ResponseEntity<>(new BaseResponse<>(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<PaginatedPostDto>> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = BlogConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = BlogConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = BlogConstant.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = BlogConstant.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return ResponseEntity.ok(new BaseResponse<>(postService.getPaginatedPosts(pageNo, pageSize, sortBy, sortDir)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> updatePost(@Valid @RequestBody PostUpdateRequest postRequest, @PathVariable(name = "id") long id){

       postService.updatePost(postRequest, id);

       return new ResponseEntity<>(new BaseResponse<>(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deletePost(@PathVariable(name = "id") long id){

        postService.deletePostById(id);

        return new ResponseEntity<>(new BaseResponse<>("Post entity deleted successfully."), HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<BaseResponse<List<PostDto>>> getPostsByCategory(@PathVariable("id") Long categoryId){
        List<PostDto> postDtos = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(new BaseResponse<>(postDtos));
    }
}