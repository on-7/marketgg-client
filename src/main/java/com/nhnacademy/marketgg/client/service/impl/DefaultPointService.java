package com.nhnacademy.marketgg.client.service.impl;

import com.nhnacademy.marketgg.client.dto.response.PointRetrieveResponse;
import com.nhnacademy.marketgg.client.repository.PointRepository;
import com.nhnacademy.marketgg.client.service.PointService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultPointService implements PointService {

    private final PointRepository pointRepository;

    @Override
    public List<PointRetrieveResponse> adminRetrievePointHistories() {
        return this.pointRepository.adminRetrievePointHistory();
    }
}
