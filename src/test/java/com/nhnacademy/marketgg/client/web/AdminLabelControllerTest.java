package com.nhnacademy.marketgg.client.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.marketgg.client.config.RedisConfig;
import com.nhnacademy.marketgg.client.dto.request.LabelRegisterRequest;
import com.nhnacademy.marketgg.client.dto.response.LabelRetrieveResponse;
import com.nhnacademy.marketgg.client.service.LabelService;
import com.nhnacademy.marketgg.client.web.admin.AdminLabelController;
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
@WebMvcTest(AdminLabelController.class)
class AdminLabelControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LabelService labelService;

    private static final String DEFAULT_LABEL = "/admin/labels";

    @Test
    @DisplayName("라벨 등록")
    void testCreateLabel() throws Exception {
        willDoNothing().given(labelService).createLabel(any(LabelRegisterRequest.class));

        this.mockMvc.perform(post(DEFAULT_LABEL + "/create")
                .param("name", "hello"))
                    .andExpect(status().is3xxRedirection());

        then(labelService).should(times(1)).createLabel(any(LabelRegisterRequest.class));
    }

    @Test
    @DisplayName("라벨 전체 조회")
    void testRetrieveLabels() throws Exception {
        given(labelService.retrieveLabels()).willReturn(List.of(new LabelRetrieveResponse()));

        this.mockMvc.perform(get(DEFAULT_LABEL))
                    .andExpect(status().isOk())
                    .andExpect(view().name("pages/admin/labels/index"));

        then(labelService).should(times(1)).retrieveLabels();
    }

    @Test
    @DisplayName("라벨 삭제")
    void testDeleteLabel() throws Exception {
        willDoNothing().given(labelService).deleteLabel(anyLong());

        this.mockMvc.perform(delete(DEFAULT_LABEL + "/{labelId}", 1L))
                    .andExpect(status().is3xxRedirection());

        then(labelService).should(times(1)).deleteLabel(anyLong());
    }

}
