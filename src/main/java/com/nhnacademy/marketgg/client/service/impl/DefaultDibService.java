package com.nhnacademy.marketgg.client.service.impl;

import com.nhnacademy.marketgg.client.dto.response.DibRetrieveResponse;
import com.nhnacademy.marketgg.client.repository.DibRepository;
import com.nhnacademy.marketgg.client.service.DibService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultDibService implements DibService {

    private final DibRepository dibRepository;

    @Override
    public void createDib(final Long productId) {
        dibRepository.createDib(productId);
    }

    @Override
    public List<DibRetrieveResponse> retrieveDibs() {
        return dibRepository.retrieveDibs();
    }

    @Override
    public void deleteDib(final Long productId) {
        dibRepository.deleteDib(productId);
    }
}
