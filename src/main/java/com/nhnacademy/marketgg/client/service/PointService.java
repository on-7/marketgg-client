package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.dto.response.PointRetrieveResponse;
import java.util.List;

public interface PointService {

    List<PointRetrieveResponse> adminRetrievePointHistories();
}
