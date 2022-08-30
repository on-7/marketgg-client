package com.nhnacademy.marketgg.client.web;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.config.RedisConfig;
import com.nhnacademy.marketgg.client.dto.response.DibRetrieveResponse;
import com.nhnacademy.marketgg.client.service.DibService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@Import(RedisConfig.class)
@WebMvcTest(DibAjaxController.class)
class DibAjaxControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DibService dibService;

    @Test
    @DisplayName("사용자 찜 여부 확인 (존재 O)")
    void testDibViewPresent() throws Exception {
        DibRetrieveResponse response = new DibRetrieveResponse();
        ReflectionTestUtils.setField(response, "productNo", 1L);

        given(dibService.retrieveDibs()).willReturn(List.of(response));

        this.mockMvc.perform(get("/dibs")
                .contentType(MediaType.APPLICATION_JSON)
                .param("productId", "1")
                .param("memberId", "1"))
                    .andExpect(status().isOk());

        then(dibService).should(times(1)).retrieveDibs();
    }

    @Test
    @DisplayName("사용자 찜 여부 확인 (존재 X)")
    void testDibViewEmpty() throws Exception {
        given(dibService.retrieveDibs()).willReturn(List.of());

        this.mockMvc.perform(post("/dibs")
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
    }

    @Test
    @DisplayName("사용자 찜 추가")
    void testDibInsert() throws Exception {
        willDoNothing().given(dibService).createDib(anyLong());

        this.mockMvc.perform(get("/dibs/insert/{productId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        then(dibService).should(times(1)).createDib(anyLong());
    }

    @Test
    @DisplayName("사용자 찜 제거")
    void testDibDelete() throws Exception {
        willDoNothing().given(dibService).deleteDib(anyLong());

        this.mockMvc.perform(get("/dibs/delete/{productId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        then(dibService).should(times(1)).deleteDib(anyLong());
    }

}
