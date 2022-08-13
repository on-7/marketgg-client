package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.request.ReviewCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ReviewUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.ReviewResponse;
import com.nhnacademy.marketgg.client.repository.ReviewRepository;
import com.nhnacademy.marketgg.client.service.ReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultReviewService implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public void createReview(final Long productId, final MemberInfo memberInfo,
                             final ReviewCreateRequest reviewRequest) throws JsonProcessingException {

        reviewRepository.createReview(productId, memberInfo, reviewRequest);
    }

    @Override
    public List<ReviewResponse> retrieveReviews(final Long productId) {
        return reviewRepository.retrieveReviews(productId);
    }

    @Override
    public ReviewResponse retrieveReview(final Long productId, final Long reviewId,
                                         final MemberInfo memberInfo) {

        return reviewRepository.retrieveReview(productId, reviewId, memberInfo);
    }

    @Override
    public void updateReview(final Long productId, final Long reviewId, final MemberInfo memberInfo,
                             final ReviewUpdateRequest reviewRequest) throws JsonProcessingException {

        reviewRepository.updateReview(productId, reviewId, memberInfo, reviewRequest);
    }

    @Override
    public void deleteReview(final Long productId, final Long reviewId, final MemberInfo memberInfo) {

        reviewRepository.deleteReview(productId, reviewId, memberInfo);
    }
}
