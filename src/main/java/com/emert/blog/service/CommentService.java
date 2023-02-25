package com.emert.blog.service;

import com.emert.blog.entity.Comment;
import com.emert.blog.entity.Post;
import com.emert.blog.exception.BlogAPIException;
import com.emert.blog.exception.ResourceNotFoundException;
import com.emert.blog.mapper.CommentMapper;
import com.emert.blog.mapper.PostMapper;
import com.emert.blog.payload.dto.CommentDto;
import com.emert.blog.payload.dto.PostDto;
import com.emert.blog.payload.request.CommentRequest;
import com.emert.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    public static final String COMMENT = "Comment";

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final CommentMapper commentMapper;
    private final PostMapper postMapper;

    public void createComment(long postId, CommentRequest commentRequest) {

        Comment comment = commentMapper.commentRequestToComment(commentRequest);
        Post post = postMapper.postDtoToPost(postService.getPostById(postId));

        comment.setPost(post);
        commentRepository.save(comment);
    }

    public List<CommentDto> getCommentsByPostId(long postId) {

        List<Comment> comments = commentRepository.findByPostId(postId);

        return commentMapper.commentListToCommentDtoList(comments);
    }

    public CommentDto getCommentById(Long postId, Long commentId) {
        PostDto post = postService.getPostById(postId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT, "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return commentMapper.commentToCommentDto(comment);
    }

    public void updateComment(Long postId, long commentId, CommentRequest commentRequest) {

        PostDto post = postService.getPostById(postId);
        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException(COMMENT, "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        comment.setBody(commentRequest.getBody());

        commentRepository.save(comment);
    }

    public void deleteComment(Long postId, Long commentId) {
        // retrieve post entity by id
        PostDto post = postService.getPostById(postId);

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException(COMMENT, "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        commentRepository.delete(comment);
    }
}