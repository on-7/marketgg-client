package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.CouponRequest;
import com.nhnacademy.marketgg.client.service.CouponService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AdminCouponController.class)
public class AdminCouponControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CouponService couponService;

    private static final String DEFAULT_COUPON = "/shop/v1/admin/coupons";

    @Test
    @DisplayName("쿠폰 등록 페이지 이동")
    void testDoCreateCoupon() throws Exception {
        mockMvc.perform(get(DEFAULT_COUPON + "/create"))
               .andExpect(view().name("/coupons/create-form"));
    }

    @Test
    @DisplayName("쿠폰 등록")
    void testCreateCoupon() throws Exception {
        CouponRequest couponRequest = new CouponRequest("couponName", "couponType", 1, 1000, 1D);

        String content = objectMapper.writeValueAsString(couponRequest);

        // willDoNothing().given(couponService).
    }

    @Test
    @DisplayName("쿠폰 목록 조회")
    void testRetrieveCoupons() throws Exception {

    }

    @Test
    @DisplayName("쿠폰 수정 페이지 이동")
    void testDoUpdateCoupon() throws Exception {

    }

    @Test
    @DisplayName("쿠폰 수정")
    void testUpdateCoupon() throws Exception {

    }

    @Test
    @DisplayName("쿠폰 삭제")
    void testDeleteCoupon() throws Exception {

    }

}
