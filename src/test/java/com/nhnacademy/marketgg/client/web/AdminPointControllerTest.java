package com.nhnacademy.marketgg.client.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.marketgg.client.service.PointService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AdminPointController.class)
class AdminPointControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PointService pointService;

    @Test
    @DisplayName("관리자의 전체 회원 포인트 내역 조회 테스트")
    void testAdminRetrieveMembersPoints() throws Exception {
        BDDMockito.given(pointService.adminRetrievePointHistories()).willReturn(List.of());

        mockMvc.perform(get("/shop/v1/admin/points"))
               .andExpect(status().isOk())
               .andExpect(view().name("/points/index"));
    }

}
