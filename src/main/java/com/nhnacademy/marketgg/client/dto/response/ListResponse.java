package com.nhnacademy.marketgg.client.dto.response;

import com.nhnacademy.marketgg.client.dto.response.common.CommonResponse;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ListResponse<T> extends CommonResponse {

    private List<T> response;
}
