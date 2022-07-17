package com.nhnacademy.marketgg.client.repository;

import com.nhnacademy.marketgg.client.dto.response.DibRetrieveResponse;

import java.util.List;

public interface DibRepository {

    void createDib(final Long memberId, final Long productId);

    List<DibRetrieveResponse> retrieveDibs(final Long id);

    void deleteDib(final Long memberId, final Long productId);

}
