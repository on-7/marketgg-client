package com.nhnacademy.marketgg.client.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.request.ReviewCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ReviewUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.ReviewResponse;
import com.nhnacademy.marketgg.client.dummy.Dummy;
import com.nhnacademy.marketgg.client.repository.ReviewRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultReviewServiceTest {

    @InjectMocks
    DefaultReviewService reviewService;

    @Mock
    ReviewRepository reviewRepository;

    private MemberInfo memberInfo;
    private ReviewCreateRequest reviewCreateRequest;
    private ReviewUpdateRequest reviewUpdateRequest;
    private ReviewResponse reviewResponse;

    @BeforeEach
    void setUp() {
        memberInfo = Dummy.getMemberInfo();
        reviewCreateRequest = Dummy.getDummyReviewCreateRequest();
        reviewUpdateRequest = Dummy.getDummyReviewUpdateRequest();
        reviewResponse = Dummy.getReviewResponse();
    }

    @Test
    @DisplayName("리뷰 생성 테스트")
    void testCreateReview() throws JsonProcessingException {
        willDoNothing().given(reviewRepository).createReview(anyLong(), any(MemberInfo.class), any(
            ReviewCreateRequest.class));

        reviewService.createReview(1L, memberInfo, reviewCreateRequest);

        then(reviewRepository).should(times(1))
                              .createReview(anyLong(), any(MemberInfo.class), any(ReviewCreateRequest.class));
    }

    @Test
    void retrieveReviews() {
        given(reviewRepository.retrieveReviews(anyLong())).willReturn(List.of(reviewResponse));

        reviewService.retrieveReviews(1L);

        then(reviewRepository).should(times(1)).retrieveReviews(anyLong());
    }

    @Test
    void retrieveReview() {
        given(reviewRepository.retrieveReview(anyLong(), anyLong())).willReturn(reviewResponse);

        reviewService.retrieveReview(1L, 1L);

        then(reviewRepository).should(times(1)).retrieveReview(anyLong(), anyLong());
    }

    @Test
    void updateReview() throws JsonProcessingException {
        willDoNothing().given(reviewRepository).updateReview(anyLong(), anyLong(), any(MemberInfo.class), any(
            ReviewUpdateRequest.class));

        reviewService.updateReview(1L, 1L, memberInfo, reviewUpdateRequest);

        then(reviewRepository).should(times(1)).updateReview(anyLong(),
                                                             anyLong(),
                                                             any(MemberInfo.class),
                                                             any(ReviewUpdateRequest.class));
    }

    @Test
    void deleteReview() {
        willDoNothing().given(reviewRepository).deleteReview(anyLong(), anyLong(), any(MemberInfo.class));

        reviewService.deleteReview(1L, 1L, memberInfo);

        then(reviewRepository).should(times(1)).deleteReview(anyLong(), anyLong(), any(MemberInfo.class));

    }
}
