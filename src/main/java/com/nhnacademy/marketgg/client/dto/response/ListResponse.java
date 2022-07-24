package com.nhnacademy.marketgg.client.dto.response;

import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class ListResponse<T> extends CommonResponse {

    private final List<T> data;
    private final Map<String, Integer> page;

    public ListResponse(List<T> data, Map<String, Integer> page) {
        super(true);
        this.data = data;
        this.page = page;
    }

}
