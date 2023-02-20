package com.emert.blog.payload.dto;

import com.emert.blog.payload.pagination.PaginationDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class PaginatedPostDto extends PaginationDto {
    List<PostDto> posts;
}
