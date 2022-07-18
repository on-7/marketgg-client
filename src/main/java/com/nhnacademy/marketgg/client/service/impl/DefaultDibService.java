package com.nhnacademy.marketgg.client.service.impl;

import com.nhnacademy.marketgg.client.dto.response.DibRetrieveResponse;
import com.nhnacademy.marketgg.client.repository.DibRepository;
import com.nhnacademy.marketgg.client.service.DibService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultDibService implements DibService {

    private final DibRepository dibRepository;

    @Override
    public void createDib(final Long memberId, final Long productId) {
        dibRepository.createDib(memberId, productId);
    }

    @Override
    public List<DibRetrieveResponse> retrieveDibs(final Long id) {
        return dibRepository.retrieveDibs(id);
    }

    @Override
    public void deleteDib(final Long memberId, final Long productId) {
        dibRepository.deleteDib(memberId, productId);
    }
}
