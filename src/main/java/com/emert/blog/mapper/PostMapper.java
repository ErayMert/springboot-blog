package com.emert.blog.mapper;

import com.emert.blog.entity.Post;
import com.emert.blog.payload.dto.PostDto;
import com.emert.blog.payload.request.PostRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface PostMapper {
    Post postRequestToPost(PostRequest request);
    PostDto postToPostDto(Post post);
    List<PostDto> postListToPostDtoList(List<Post> posts);

}
