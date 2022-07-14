package com.nhnacademy.marketgg.client.web;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.marketgg.client.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Test
    @DisplayName("GG 패스 메인 페이지")
    void testIndex() throws Exception {
        mockMvc.perform(get("/shop/v1/members/{memberId}/ggpass", 1L))
               .andExpect(status().isOk())
               .andExpect(view().name("/ggpass/index"));
    }

    @Test
    @DisplayName("GG 패스 구독 성공")
    void testSubscribePassSuccess() throws Exception {
        doNothing().when(memberService).subscribePass(anyLong());
        when(memberService.checkPassUpdatedAt(anyLong())).thenReturn(Boolean.FALSE);

        mockMvc.perform(post("/shop/v1/members/{memberId}/ggpass/subscribe", 1L))
               .andExpect(status().is3xxRedirection());

        verify(memberService, times(1)).subscribePass(anyLong());
    }

    @Test
    @DisplayName("GG 패스 구독 실패")
    void testSubscribePassFail() throws Exception {
        doNothing().when(memberService).subscribePass(anyLong());
        when(memberService.checkPassUpdatedAt(anyLong())).thenReturn(Boolean.TRUE);

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

}