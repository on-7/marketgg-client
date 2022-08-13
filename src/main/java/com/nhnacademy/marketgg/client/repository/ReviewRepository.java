package com.nhnacademy.marketgg.client.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.request.ReviewCreateRequest;
import com.nhnacademy.marketgg.client.dto.response.ReviewResponse;
import java.util.List;

public interface ReviewRepository {

    void createReview(final Long productId, final Long reviewId, final MemberInfo memberInfo,
                      final ReviewCreateRequest reviewRequest) throws JsonProcessingException;

    List<ReviewResponse> retrieveReviews(final Long productId, final MemberInfo memberInfo);

    ReviewResponse retrieveReview(final Long productId, final Long reviewId, final MemberInfo memberInfo);
}
