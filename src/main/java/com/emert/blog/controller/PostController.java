package com.emert.blog.controller;

import com.emert.blog.constant.BlogConstant;
import com.emert.blog.mapper.PostMapper;
import com.emert.blog.payload.base.BaseResponse;
import com.emert.blog.payload.dto.PaginatedPostDto;
import com.emert.blog.payload.request.PostRequest;
import com.emert.blog.payload.request.PostUpdateRequest;
import com.emert.blog.payload.response.PostResponse;
import com.emert.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{categoryId}")
    public ResponseEntity<BaseResponse<Void>> createPost(@PathVariable Long categoryId, @Valid @RequestBody PostRequest postRequest){
        postService.createPost(categoryId, postRequest);
        return new ResponseEntity<>(new BaseResponse<>(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<PageImpl<PostResponse>>> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = BlogConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = BlogConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = BlogConstant.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = BlogConstant.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){

        PaginatedPostDto paginatedPosts = postService.getPaginatedPosts(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(new BaseResponse<>(new PageImpl<>(postMapper.postDtoListToPostResponseList(paginatedPosts.getPosts()), paginatedPosts.getPageable(), paginatedPosts.getCount())));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(postMapper.postDtoToPostResponse(postService.getPostById(id)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> updatePost(@Valid @RequestBody PostUpdateRequest postRequest, @PathVariable(name = "id") long id){

       postService.updatePost(postRequest, id);

       return new ResponseEntity<>(new BaseResponse<>(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deletePost(@PathVariable(name = "id") Long id){

        postService.deletePostById(id);
        return ResponseEntity.ok(new BaseResponse<>());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<BaseResponse<List<PostResponse>>> getPostsByCategory(@PathVariable("id") Long categoryId){
        List<PostResponse> posts = postMapper.postDtoListToPostResponseList(postService.getPostsByCategory(categoryId));
        return ResponseEntity.ok(new BaseResponse<>(posts));
    }
}