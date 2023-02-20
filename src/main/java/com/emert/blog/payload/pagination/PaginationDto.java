package com.emert.blog.payload.pagination;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class PaginationDto {

    private long count;
    private int page;
    private int size;
    private Pageable pageable;
    private int totalPageNumber;
}
