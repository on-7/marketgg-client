package com.nhnacademy.marketgg.client.repository.point;

import com.nhnacademy.marketgg.client.dto.point.PointRetrieveResponse;
import java.util.List;

public interface PointRepository {

    List<PointRetrieveResponse> adminRetrievePointHistory();

    PointRetrieveResponse retrievePointHistory(final Long id);

}
