package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.repository.DibRepository;
import com.nhnacademy.marketgg.client.service.impl.DefaultDibService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
public class DibServiceTest {

    @InjectMocks
    private DefaultDibService dibService;

    @Mock
    private DibRepository dibRepository;

    @Test
    @DisplayName("찜 등록")
    void testCreateDib() {
        willDoNothing().given(dibRepository).createDib(anyLong(), anyLong());

        dibService.createDib(1L, 1L);

        then(dibRepository).should().createDib(anyLong(), anyLong());
    }

    @Test
    @DisplayName("회원의 모든 찜 목록 조회")
    void testRetrieveDibs() {
        given(dibRepository.retrieveDibs(anyLong())).willReturn(List.of());

        dibService.retrieveDibs(1L);

        then(dibRepository).should().retrieveDibs(anyLong());
    }

    @Test
    @DisplayName("찜 삭제")
    void testDeleteDib() {
        willDoNothing().given(dibRepository).deleteDib(anyLong(), anyLong());

        dibService.deleteDib(1L, 1L);

        then(dibRepository).should().deleteDib(anyLong(), anyLong());
    }
    
}
