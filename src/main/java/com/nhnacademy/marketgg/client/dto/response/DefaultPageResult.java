package com.nhnacademy.marketgg.client.dto.response;


import com.nhnacademy.marketgg.client.dto.response.common.CommonResponse;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 기본 페이지 응답 결과 객체입니다.
 */
@NoArgsConstructor
@Getter
public class DefaultPageResult<T> extends CommonResponse {

    private List<T> data;
    private Long totalElements;
    private Integer pageNumber;
    private Integer offset;

    public DefaultPageResult(List<T> data, Long totalElements, Integer pageNumber, Integer offset) {
        this.data = data;
        this.totalElements = totalElements;
        this.pageNumber = pageNumber;
        this.offset = offset;
    }

}
