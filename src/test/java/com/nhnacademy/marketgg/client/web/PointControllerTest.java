package com.nhnacademy.marketgg.client.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.marketgg.client.dto.response.PointRetrieveResponse;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.service.PointService;
import com.nhnacademy.marketgg.client.web.member.PointController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(PointController.class)
class PointControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PointService pointService;

    @MockBean
    RedisTemplate<String, JwtInfo> redisTemplate;

    @Test
    @DisplayName("회원의 자신의 포인트 적립 내역 조회 테스트")
    void testRetrieveMemberPoint() throws Exception {
        Long id = 1L;
        given(pointService.retrievePointHistories(id)).willReturn(new PointRetrieveResponse());

        mockMvc.perform(MockMvcRequestBuilders.get("/members/" + id + "/points"))
               .andExpect(status().isOk())
               .andExpect(view().name("pages/points/index"));

        then(pointService).should(times(1)).retrievePointHistories(id);
    }

}
