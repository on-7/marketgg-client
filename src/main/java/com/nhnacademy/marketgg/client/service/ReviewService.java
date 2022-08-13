package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.request.ReviewCreateRequest;

public interface ReviewService {

    void createReview(final Long productId, final Long reviewId, final MemberInfo memberInfo,
                      final ReviewCreateRequest reviewRequest) throws JsonProcessingException;

}
