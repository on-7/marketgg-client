package com.nhnacademy.marketgg.client.dto.common;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PageResult<T> {

    private Integer pageNumber;

    private Integer pageSize;

    private Integer totalPages; // 총 페이지 수

    private List<T> data;
}
