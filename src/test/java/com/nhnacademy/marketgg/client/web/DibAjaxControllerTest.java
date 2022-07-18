package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.response.DibRetrieveResponse;
import com.nhnacademy.marketgg.client.service.DibService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
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
        given(dibService.retrieveDibs(anyLong())).willReturn(List.of(response));
        mockMvc.perform(post("/dibs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("productId", "1")
                                .param("memberId", "1"))
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("사용자 찜 여부 확인 (존재 X)")
    void testDibViewEmpty() throws Exception {
        given(dibService.retrieveDibs(anyLong())).willReturn(List.of());
        mockMvc.perform(post("/dibs")
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("사용자 찜 추가")
    void testDibInsert() throws Exception {
        willDoNothing().given(dibService).createDib(anyLong(), anyLong());
        mockMvc.perform(get("/dibs/insert/{memberId}/{productId}", 1L, 1L)
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
        verify(dibService, times(1)).createDib(anyLong(), anyLong());
    }

    @Test
    @DisplayName("사용자 찜 제거")
    void testDibDelete() throws Exception {
        willDoNothing().given(dibService).deleteDib(anyLong(), anyLong());
        mockMvc.perform(get("/dibs/delete/{memberId}/{productId}", 1L, 1L)
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
        verify(dibService, times(1)).deleteDib(anyLong(), anyLong());
    }

}
