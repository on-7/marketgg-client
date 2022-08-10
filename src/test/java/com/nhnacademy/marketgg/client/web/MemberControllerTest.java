package com.nhnacademy.marketgg.client.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.EmailRequest;
import com.nhnacademy.marketgg.client.dto.request.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.client.dto.response.EmailUseResponse;
import com.nhnacademy.marketgg.client.service.GivenCouponService;
import com.nhnacademy.marketgg.client.service.MemberService;
import com.nhnacademy.marketgg.client.web.member.MemberAjaxController;
import com.nhnacademy.marketgg.client.web.member.MemberController;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

@AutoConfigureMockMvc(addFilters = false)
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

    @Test
    @DisplayName("GG 패스 메인 페이지")
    void testIndex() throws Exception {
        given(memberService.retrievePassUpdatedAt()).willReturn(LocalDateTime.now());
        mockMvc.perform(get("/members/ggpass"))
               .andExpect(status().isOk())
               .andExpect(view().name("/ggpass/index"));
    }

    @Test
    @DisplayName("GG 패스 구독 성공")
    void testSubscribePassSuccess() throws Exception {
        given(memberService.retrievePassUpdatedAt()).willReturn(LocalDateTime.now());
        willDoNothing().given(memberService).subscribePass();

        mockMvc.perform(post("/members/ggpass/subscribe"))
               .andExpect(status().is3xxRedirection());

        then(memberService).should(times(1)).subscribePass();
    }

    @Test
    @DisplayName("GG 패스 구독 실패")
    void testSubscribePassFail() throws Exception {
        given(memberService.retrievePassUpdatedAt()).willReturn(LocalDateTime.of(2033, 12, 1, 12, 30));
        willDoNothing().given(memberService).subscribePass();

        mockMvc.perform(post("/members/ggpass/subscribe"))
               .andExpect(view().name("message"));

        then(memberService).should(times(0)).subscribePass();
    }

    @Test
    @DisplayName("GG 패스 구독해지")
    void testWithdrawPass() throws Exception {
        willDoNothing().given(memberService).withdrawPass();

        mockMvc.perform(post("/members/ggpass/withdraw"))
               .andExpect(status().is3xxRedirection());

        then(memberService).should(times(1)).withdrawPass();
    }

    @Test
    @DisplayName("사용할 수 있는 이메일 검증 테스트")
    void testUseEmail() throws Exception {

        given(memberService.useEmail(any()))
                .willReturn(new EmailUseResponse(false));

        ObjectMapper objectMapper = new ObjectMapper();

        boolean hasNotReferrer = false;

        mockMvc.perform(post("/marketgg/members/use/email")
                                .content(objectMapper.writeValueAsString(
                                        new EmailRequest("aaa@naver.com", hasNotReferrer)))
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원의 쿠폰 등록")
    void testRegisterCoupon() throws Exception {
        GivenCouponCreateRequest givenCouponRequest = new GivenCouponCreateRequest("couponName");

        String content = objectMapper.writeValueAsString(givenCouponRequest);

        willDoNothing().given(givenCouponService).registerCoupon(anyLong(), any(GivenCouponCreateRequest.class));

        mockMvc.perform(post("/members/{memberId}/coupons", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
               .andExpect(status().is3xxRedirection());

        then(givenCouponService).should().registerCoupon(anyLong(), any(GivenCouponCreateRequest.class));
    }

    @Test
    @DisplayName("회원 보유 쿠폰 목록 조회")
    void testRetrieveOwnCoupons() throws Exception {
        given(givenCouponService.retrieveOwnGivenCoupons(anyLong())).willReturn(List.of());

        MvcResult mvcResult = mockMvc.perform(get("/members/{memberId}/coupons", 1L))
                                     .andExpect(status().isOk())
                                     .andExpect(view().name("/mygg/coupons/index"))
                                     .andReturn();
        Map<String, Object> resultModel = Objects.requireNonNull(mvcResult.getModelAndView()).getModel();

        then(givenCouponService).should().retrieveOwnGivenCoupons(anyLong());
        assertThat(resultModel.get("coupons")).isInstanceOf(List.class);
        assertThat(resultModel.get("memberId")).isInstanceOf(Long.class);
    }

}
