package com.nhnacademy.marketgg.client.service.point;

import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.point.PointRetrieveResponse;
import java.util.List;

public interface PointService {

    List<PointRetrieveResponse> adminRetrievePointHistories();

    PageResult<PointRetrieveResponse> retrievePointHistories(final int page);
}
