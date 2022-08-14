package com.nhnacademy.marketgg.client.web;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.request.ReviewCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ReviewUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.ReviewResponse;
import com.nhnacademy.marketgg.client.dummy.Dummy;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.service.ReviewService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ReviewService reviewService;

    @MockBean
    RedisTemplate<String, JwtInfo> redisTemplate;

    private static final String DEFAULT_PRODUCT = "/products";
    private static final String PRODUCT_ID = "/1";

    private ReviewCreateRequest reviewCreateRequest;
    private ReviewUpdateRequest reviewUpdateRequest;
    private MemberInfo memberInfo;
    private ReviewResponse reviewResponse;

    @BeforeEach
    void setUp() {
        reviewCreateRequest = Dummy.getDummyReviewCreateRequest();
        reviewUpdateRequest = Dummy.getDummyReviewUpdateRequest();
        memberInfo = Dummy.getMemberInfo();
        reviewResponse = Dummy.getReviewResponse();
    }

    @Test
    @DisplayName("후기 등록 테스트")
    void testCreateReview() throws Exception {
        willDoNothing().given(reviewService)
                       .createReview(anyLong(), any(MemberInfo.class), any(ReviewCreateRequest.class));

        String reviewRequest = objectMapper.writeValueAsString(reviewCreateRequest);

        this.mockMvc.perform(post(DEFAULT_PRODUCT + PRODUCT_ID + "/reviews")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(reviewRequest)
                                 .content(objectMapper.writeValueAsString(memberInfo)))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/" + "products/product-view"));

        then(reviewService).should(times(1))
                           .createReview(anyLong(), any(MemberInfo.class), any(ReviewCreateRequest.class));
    }

    @Test
    @DisplayName("후기 전체조회 테스트")
    void testRetrieveReviews() throws Exception {
        given(reviewService.retrieveReviews(anyLong())).willReturn(List.of(
            reviewResponse));

        this.mockMvc.perform(get(DEFAULT_PRODUCT + PRODUCT_ID + "/reviews"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("products/reviews/review-view"));

        then(reviewService).should(times(1))
                           .retrieveReviews(anyLong());
    }

    @Test
    @DisplayName("후기 상세조회 테스트")
    void testRetrieveReview() throws Exception {
        given(reviewService.retrieveReview(anyLong(), anyLong())).willReturn(reviewResponse);

        this.mockMvc.perform(get(DEFAULT_PRODUCT + PRODUCT_ID + "/reviews" + "/{reviewId}", 1))
                    .andExpect(status().isOk())
                    .andExpect(view().name("products/reviews/review-view"));

        then(reviewService).should(times(1)).retrieveReview(anyLong(), anyLong());
    }


    @Test
    @DisplayName("후기 수정 테스트")
    void testUpdateReview() throws Exception {
        willDoNothing().given(reviewService).updateReview(anyLong(), anyLong(), any(MemberInfo.class), any(
            ReviewUpdateRequest.class));

        String updateRequest = objectMapper.writeValueAsString(reviewUpdateRequest);

        this.mockMvc.perform(put(DEFAULT_PRODUCT + PRODUCT_ID + "/reviews" + "/{reviewId}", 1)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(memberInfo))
                                 .content(updateRequest))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/" + "products/product-view"));

        then(reviewService).should(times(1)).updateReview(anyLong(),
                                                          anyLong(),
                                                          any(MemberInfo.class),
                                                          any(ReviewUpdateRequest.class));
    }

    @Test
    @DisplayName("후기 삭제 테스트")
    void testDeleteReview() throws Exception {
        willDoNothing().given(reviewService).deleteReview(anyLong(), anyLong(), any(MemberInfo.class));

        this.mockMvc.perform(delete(DEFAULT_PRODUCT + PRODUCT_ID + "/reviews" + "/{reviewId}", 1L)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(memberInfo)))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/" + "products/product-view"));

        then(reviewService).should(times(1)).deleteReview(anyLong(), anyLong(), any(MemberInfo.class));
    }
}