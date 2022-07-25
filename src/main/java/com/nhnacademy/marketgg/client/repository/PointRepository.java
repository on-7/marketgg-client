package com.nhnacademy.marketgg.client.repository;

import com.nhnacademy.marketgg.client.dto.response.PointRetrieveResponse;
import java.util.List;

public interface PointRepository {

    List<PointRetrieveResponse> adminRetrievePointHistory();

    PointRetrieveResponse retrievePointHistory(final Long id);

}
