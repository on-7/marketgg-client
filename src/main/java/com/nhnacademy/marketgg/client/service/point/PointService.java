package com.nhnacademy.marketgg.client.service.point;

import com.nhnacademy.marketgg.client.dto.point.PointRetrieveResponse;
import java.util.List;

public interface PointService {

    List<PointRetrieveResponse> adminRetrievePointHistories();

    PointRetrieveResponse retrievePointHistories(final Long id);
}
