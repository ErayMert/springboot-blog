package com.emert.blog.service;

import com.emert.blog.entity.Category;
import com.emert.blog.entity.Post;
import com.emert.blog.exception.ResourceNotFoundException;
import com.emert.blog.mapper.PostMapper;
import com.emert.blog.payload.dto.PaginatedPostDto;
import com.emert.blog.payload.dto.PostDto;
import com.emert.blog.payload.request.PostRequest;
import com.emert.blog.payload.request.PostUpdateRequest;
import com.emert.blog.repository.CategoryRepository;
import com.emert.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService{

    public static final String CATEGORY = "Category";
    public static final String POST = "Post";

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final CategoryRepository categoryRepository;


    public void createPost(Long categoryId, PostRequest postRequest) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY, "id", categoryId));

        Post post = postMapper.postRequestToPost(postRequest);
        post.setCategory(category);
        postRepository.save(post);
    }

    public PaginatedPostDto getPaginatedPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        List<PostDto> content = postMapper.postListToPostDtoList(posts.getContent());

        return PaginatedPostDto.builder()
                .pageable(pageable)
                .posts(content)
                .count(posts.getTotalElements())
                .page(posts.getPageable().getPageNumber())
                .size(posts.getPageable().getPageSize())
                .totalPageNumber(posts.getTotalPages())
                .build();
    }

    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(POST, "id", id));
        return postMapper.postToPostDto(post);
    }

    public void updatePost(PostUpdateRequest postRequest, long id) {
        // get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(POST, "id", id));

        Category category = categoryRepository.findById(postRequest.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException(CATEGORY, "id", postRequest.getCategoryId()));

        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setContent(postRequest.getContent());
        post.setCategory(category);
        postRepository.save(post);
    }

    public void deletePostById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(POST, "id", id));
        postRepository.delete(post);
    }

    public List<PostDto> getPostsByCategory(Long categoryId) {

        if(Boolean.FALSE.equals(categoryRepository.existsById(categoryId))){
            throw new ResourceNotFoundException("The category does not exist");
        }

        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return postMapper.postListToPostDtoList(posts);
    }

}