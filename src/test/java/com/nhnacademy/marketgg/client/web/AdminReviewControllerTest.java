package com.nhnacademy.marketgg.client.web;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.config.RedisConfig;
import com.nhnacademy.marketgg.client.service.review.ReviewService;
import com.nhnacademy.marketgg.client.web.admin.AdminReviewController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@Import(RedisConfig.class)
@WebMvcTest(AdminReviewController.class)
class AdminReviewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ReviewService reviewService;

    @Test
    @DisplayName("베스트 후기 선정 테스트")
    void testMakeBestReview() throws Exception {
        willDoNothing().given(reviewService).makeBestReview(anyLong(), anyLong());

        mockMvc.perform(post("/admin/products/{productId}/reviews/{reviewId}/make-best", 1, 1))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/products/1"));

        then(reviewService).should(times(1)).makeBestReview(anyLong(), anyLong());

    }
}
