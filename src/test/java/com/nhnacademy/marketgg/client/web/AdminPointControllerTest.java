package com.nhnacademy.marketgg.client.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.marketgg.client.config.RedisConfig;
import com.nhnacademy.marketgg.client.dto.point.PointRetrieveResponse;
import com.nhnacademy.marketgg.client.service.point.PointService;
import com.nhnacademy.marketgg.client.web.admin.AdminPointController;
import java.util.List;
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
@WebMvcTest(AdminPointController.class)
class AdminPointControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PointService pointService;

    @Test
    @DisplayName("관리자의 전체 회원 포인트 내역 조회 테스트")
    void testAdminRetrieveMembersPoints() throws Exception {
        given(pointService.adminRetrievePointHistories()).willReturn(List.of(new PointRetrieveResponse()));

        this.mockMvc.perform(get("/admin/points"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("pages/points/admin-retrieve-members"));

        then(pointService).should(times(1)).adminRetrievePointHistories();
    }

}
