package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.repository.DibRepository;
import com.nhnacademy.marketgg.client.service.impl.DefaultDibService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DibServiceTest {

    @InjectMocks
    private DefaultDibService dibService;

    @Mock
    private DibRepository dibRepository;

    @Test
    @DisplayName("찜 등록")
    void testCreateDib() {

    }

    @Test
    @DisplayName("회원의 모든 찜 목록 조회")
    void testRetrieveDibs() {

    }

    @Test
    @DisplayName("찜 삭제")
    void testDeleteDib() {

    }
    
}
