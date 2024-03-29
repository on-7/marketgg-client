package com.nhnacademy.marketgg.client.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.config.RedisConfig;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.member.EmailRequest;
import com.nhnacademy.marketgg.client.dto.coupon.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.client.dto.member.EmailUseResponse;
import com.nhnacademy.marketgg.client.dto.coupon.GivenCouponRetrieveResponse;
import com.nhnacademy.marketgg.client.service.coupon.GivenCouponService;
import com.nhnacademy.marketgg.client.service.member.MemberService;
import com.nhnacademy.marketgg.client.service.product.ProductInquiryService;
import com.nhnacademy.marketgg.client.service.review.ReviewService;
import com.nhnacademy.marketgg.client.web.member.MemberAjaxController;
import com.nhnacademy.marketgg.client.web.member.MemberController;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.client.RestTemplate;

@AutoConfigureMockMvc(addFilters = false)
@Import(RedisConfig.class)
@WebMvcTest({
    MemberController.class,
    MemberAjaxController.class
})
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;

    @MockBean
    RestTemplate restTemplate;

    @MockBean
    GivenCouponService givenCouponService;

    @MockBean
    ProductInquiryService productInquiryService;

    @MockBean
    ReviewService reviewService;

    @Test
    @DisplayName("사용할 수 있는 이메일 검증 테스트")
    void testUseEmail() throws Exception {

        given(memberService.useEmail(any(EmailRequest.class))).willReturn(new EmailUseResponse(false));

        ObjectMapper objectMapper = new ObjectMapper();

        boolean hasNotReferrer = false;

        mockMvc.perform(post("/marketgg/members/use/email")
                   .content(objectMapper.writeValueAsString(
                       new EmailRequest("aaa@naver.com", hasNotReferrer)))
                   .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());

        then(memberService).should(times(1)).useEmail(any(EmailRequest.class));
    }

    @Test
    @DisplayName("회원의 쿠폰 등록")
    void testRegisterCoupon() throws Exception {
        GivenCouponCreateRequest givenCouponRequest = new GivenCouponCreateRequest("couponName");

        String content = objectMapper.writeValueAsString(givenCouponRequest);

        willDoNothing().given(givenCouponService).registerCoupon(any(GivenCouponCreateRequest.class));

        mockMvc.perform(post("/members/coupons", 1L)
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(content))
               .andExpect(status().is3xxRedirection());

        then(givenCouponService).should(times(1)).registerCoupon(any(GivenCouponCreateRequest.class));
    }

    // @Test
    @DisplayName("회원 보유 쿠폰 목록 조회")
    void testRetrieveOwnCoupons() throws Exception {
        PageResult<GivenCouponRetrieveResponse> result = new PageResult<>();
        given(givenCouponService.retrieveOwnGivenCoupons(1)).willReturn(result);

        MvcResult mvcResult = mockMvc.perform(get("/members/coupons"))
                                     .andExpect(status().isOk())
                                     .andExpect(view().name("/pages/mygg/coupons/index"))
                                     .andReturn();
        Map<String, Object> resultModel = Objects.requireNonNull(mvcResult.getModelAndView()).getModel();

        then(givenCouponService).should(times(1)).retrieveOwnGivenCoupons(1);
        assertThat(resultModel.get("coupons")).isInstanceOf(List.class);
    }

}
