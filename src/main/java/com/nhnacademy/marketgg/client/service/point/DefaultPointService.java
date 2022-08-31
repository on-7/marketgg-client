package com.nhnacademy.marketgg.client.service.point;

import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.point.PointRetrieveResponse;
import com.nhnacademy.marketgg.client.repository.point.PointRepository;
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

    @Override
    public PageResult<PointRetrieveResponse> retrievePointHistories(final int page) {
        return this.pointRepository.retrievePointHistory(page);
    }
}
