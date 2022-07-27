package com.nhnacademy.marketgg.client.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.EmailRequest;
import com.nhnacademy.marketgg.client.dto.response.EmailUseResponse;
import com.nhnacademy.marketgg.client.service.MemberService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest({
        MemberController.class,
        MemberAjaxController.class
})
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @MockBean
    RestTemplate restTemplate;

    @Test
    @DisplayName("GG 패스 메인 페이지")
    void testIndex() throws Exception {
        when(memberService.retrievePassUpdatedAt(anyLong())).thenReturn(LocalDateTime.now());
        mockMvc.perform(get("/shop/v1/members/{memberId}/ggpass", 1L))
               .andExpect(status().isOk())
               .andExpect(view().name("/ggpass/index"));
    }

    @Test
    @DisplayName("GG 패스 구독 성공")
    void testSubscribePassSuccess() throws Exception {
        doNothing().when(memberService).subscribePass(anyLong());
        when(memberService.retrievePassUpdatedAt(anyLong())).thenReturn(LocalDateTime.now());

        mockMvc.perform(post("/shop/v1/members/{memberId}/ggpass/subscribe", 1L))
               .andExpect(status().is3xxRedirection());

        verify(memberService, times(1)).subscribePass(anyLong());
    }

    @Test
    @DisplayName("GG 패스 구독 실패")
    void testSubscribePassFail() throws Exception {
        doNothing().when(memberService).subscribePass(anyLong());
        when(memberService.retrievePassUpdatedAt(anyLong())).thenReturn(
                LocalDateTime.of(2030, 1, 1, 1, 1, 1));

        mockMvc.perform(post("/shop/v1/members/{memberId}/ggpass/subscribe", 1L))
               .andExpect(view().name("message"));

        verify(memberService, times(0)).subscribePass(anyLong());
    }

    @Test
    @DisplayName("GG 패스 구독해지")
    void testWithdrawPass() throws Exception {
        doNothing().when(memberService).withdrawPass(anyLong());

        mockMvc.perform(post("/shop/v1/members/{memberId}/ggpass/withdraw", 1L))
               .andExpect(status().is3xxRedirection());

        verify(memberService, times(1)).withdrawPass(anyLong());
    }

    @Test
    @DisplayName("사용할 수 있는 이메일 검증 테스트")
    void testUseEmail() throws Exception {

        given(memberService.useEmail(any()))
                .willReturn(new EmailUseResponse(false));

        ObjectMapper objectMapper = new ObjectMapper();

        boolean hasNotReferrer = false;

        mockMvc.perform(post("/marketgg/members/use/email")
                       .content(objectMapper.writeValueAsString(new EmailRequest("aaa@naver.com", hasNotReferrer)))
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

}
