package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.service.DibService;
import com.nhnacademy.marketgg.client.web.member.DibController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(DibController.class)
class DibControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DibService dibService;

    @MockBean
    RedisTemplate<String, JwtInfo> redisTemplate;

    private static final String DEFAULT_DIB = "/members/dibs";

    @Test
    @DisplayName("찜 목록 조회")
    void testRetrieveDibs() throws Exception {
        given(dibService.retrieveDibs()).willReturn(List.of());

        this.mockMvc.perform(get(DEFAULT_DIB))
                    .andExpect(status().isOk())
                    .andExpect(view().name("/pages/dibs/index"));

        then(dibService).should(times(1)).retrieveDibs();
    }

    @Test
    @DisplayName("찜 목록에서 찜 제거")
    void testDeleteDib() throws Exception {
        willDoNothing().given(dibService).deleteDib(anyLong());

        this.mockMvc.perform(delete(DEFAULT_DIB + "/{productId}", 1L))
                    .andExpect(status().is3xxRedirection());

        then(dibService).should(times(1)).deleteDib(anyLong());
    }

}
