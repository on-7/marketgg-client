package com.nhnacademy.marketgg.client.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import com.nhnacademy.marketgg.client.config.RedisConfig;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.coupon.CouponRequest;
import com.nhnacademy.marketgg.client.dto.coupon.CouponRetrieveResponse;
import com.nhnacademy.marketgg.client.service.coupon.CouponService;
import com.nhnacademy.marketgg.client.web.admin.AdminCouponController;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc(addFilters = false)
@Import(RedisConfig.class)
@WebMvcTest(AdminCouponController.class)
class AdminCouponControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CouponService couponService;

    private static final String DEFAULT_COUPON = "/admin/coupons";

    @Test
    @DisplayName("쿠폰 등록 페이지 이동")
    void testDoCreateCoupon() throws Exception {
        this.mockMvc.perform(get(DEFAULT_COUPON + "/create"))
                    .andExpect(view().name("pages/admin/coupons/create-form"));
    }

    @Test
    @DisplayName("쿠폰 등록")
    void testCreateCoupon() throws Exception {
        CouponRequest couponRequest = new CouponRequest("couponName", "couponType", 1, 1000, 1D);

        String content = objectMapper.writeValueAsString(couponRequest);

        willDoNothing().given(couponService).createCoupon(any(CouponRequest.class));

        this.mockMvc.perform(post(DEFAULT_COUPON)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(content));

        then(couponService).should(times(1)).createCoupon(any(CouponRequest.class));
    }

    // @Test
    @DisplayName("쿠폰 목록 조회")
    void testRetrieveCoupons() throws Exception {
        given(couponService.retrieveCoupons(anyInt())).willReturn(new PageResult<>());

        this.mockMvc.perform(get(DEFAULT_COUPON + "/index"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("pages/admin/coupons/index"));

        then(couponService).should(times(1)).retrieveCoupons(1);
    }

    @Test
    @DisplayName("쿠폰 수정 페이지 이동")
    void testDoUpdateCoupon() throws Exception {
        given(couponService.retrieveCoupon(anyLong())).willReturn(new CouponRetrieveResponse());

        MvcResult mvcResult = this.mockMvc.perform(get(DEFAULT_COUPON + "/update/" + 1L))
                                          .andExpect(view().name("pages/admin/coupons/update-form"))
                                          .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView())
                          .getModel().get("coupon")).isInstanceOf(CouponRetrieveResponse.class);
    }

    @Test
    @DisplayName("쿠폰 수정")
    void testUpdateCoupon() throws Exception {
        CouponRequest couponRequest = new CouponRequest("couponName", "couponType", 1, 1000, 1D);

        String content = objectMapper.writeValueAsString(couponRequest);

        willDoNothing().given(couponService).updateCoupon(anyLong(), any(CouponRequest.class));

        this.mockMvc.perform(put(DEFAULT_COUPON + "/" + 1L)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(content));

        then(couponService).should(times(1)).updateCoupon(anyLong(), any(CouponRequest.class));
    }

    @Test
    @DisplayName("쿠폰 삭제")
    void testDeleteCoupon() throws Exception {
        willDoNothing().given(couponService).deleteCoupon(anyLong());

        this.mockMvc.perform(delete(DEFAULT_COUPON + "/" + 1L))
                    .andExpect(status().is3xxRedirection());

        then(couponService).should(times(1)).deleteCoupon(anyLong());
    }

}
