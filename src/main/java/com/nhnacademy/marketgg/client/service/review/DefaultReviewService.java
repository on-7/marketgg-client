package com.nhnacademy.marketgg.client.service.review;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.common.MemberInfo;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.review.ReviewCreateRequest;
import com.nhnacademy.marketgg.client.dto.review.ReviewRatingResponse;
import com.nhnacademy.marketgg.client.dto.review.ReviewResponse;
import com.nhnacademy.marketgg.client.dto.review.ReviewUpdateRequest;
import com.nhnacademy.marketgg.client.repository.review.ReviewRepository;
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
    public PageResult<ReviewResponse> retrieveReviews(final Long productId, final int page) {
        return reviewRepository.retrieveReviews(productId, page);
    }

    @Override
    public ReviewResponse retrieveReview(final Long productId, final Long reviewId) {

        return reviewRepository.retrieveReview(productId, reviewId);
    }

    @Override
    public CommonResult<List<ReviewRatingResponse>> retrieveReviewsByRating(final Long productId) {
        return reviewRepository.retrieveReviewsByRating(productId);
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

    @Override
    public void makeBestReview(final Long productId, final Long reviewId) {
        reviewRepository.makeBestReview(productId, reviewId);
    }

    @Override
    public PageResult<ReviewResponse> retrieveReviewsByMember(MemberInfo memberInfo, int page) {
        return reviewRepository.retrieveReviewsByMember(memberInfo, page);
    }
}
