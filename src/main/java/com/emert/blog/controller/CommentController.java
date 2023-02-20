package com.emert.blog.controller;


import com.emert.blog.payload.base.BaseResponse;
import com.emert.blog.payload.dto.CommentDto;
import com.emert.blog.payload.request.CommentRequest;
import com.emert.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/{postId}")
    public ResponseEntity<BaseResponse<Void>> createComment(@PathVariable(value = "postId") long postId,
                                                                 @Valid @RequestBody CommentRequest request){
        commentService.createComment(postId, request);
        return new ResponseEntity<>(new BaseResponse<>(), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<BaseResponse<List<CommentDto>>> getCommentsByPostId(@PathVariable(value = "postId") Long postId){

        List<CommentDto> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(new BaseResponse<>(comments));
    }

    @GetMapping("/{postId}/{id}")
    public ResponseEntity<BaseResponse<CommentDto>> getCommentById(@PathVariable(value = "postId") Long postId,
                                                     @PathVariable(value = "id") Long commentId){
        CommentDto commentDto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(new BaseResponse<>(commentDto), HttpStatus.OK);
    }

    @PutMapping("/{postId}/{id}")
    public ResponseEntity<BaseResponse<Void>> updateComment(@PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value = "id") Long commentId,
                                                    @Valid @RequestBody CommentRequest request){

        commentService.updateComment(postId, commentId, request);
        return new ResponseEntity<>(new BaseResponse<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "id") Long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>(new BaseResponse<>(), HttpStatus.OK);
    }
}