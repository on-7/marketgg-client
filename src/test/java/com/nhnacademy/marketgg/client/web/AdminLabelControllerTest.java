package com.nhnacademy.marketgg.client.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.marketgg.client.dto.LabelRegisterRequest;
import com.nhnacademy.marketgg.client.service.LabelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AdminLabelController.class)
class AdminLabelControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LabelService labelService;

    @Test
    @DisplayName("라벨 메인 페이지 이동")
    void testIndex() throws Exception {
        mockMvc.perform(get("/admin/v1/labels/index"))
                .andExpect(view().name("/labels/index"));
    }

    @Test
    @DisplayName("라벨 등록 페이지 이동")
    void testDoCreateLabel() throws Exception {
        mockMvc.perform(get("/admin/v1/labels"))
               .andExpect(view().name("/labels/create-form"));
    }

    @Test
    @DisplayName("라벨 등록")
    void testCreateLabel() throws Exception {
        doNothing().when(labelService).createLabel(any(LabelRegisterRequest.class));

        mockMvc.perform(post("/admin/v1/labels")
                                .param("name", "hello"))
               .andExpect(status().is3xxRedirection());

        verify(labelService, times(1)).createLabel(any(LabelRegisterRequest.class));
    }

}
