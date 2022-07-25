package com.nhnacademy.marketgg.client.dto.request;

import com.nhnacademy.marketgg.client.dto.request.searchutil.BoolQuery;
import com.nhnacademy.marketgg.client.dto.request.searchutil.Sort;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SearchToCategoryRequestBody {

    private List<Sort> sort;

    private Integer from;

    private Integer size;

    private BoolQuery query;


}
