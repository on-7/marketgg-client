package com.nhnacademy.marketgg.client.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.DibRepository;
import com.nhnacademy.marketgg.client.service.impl.DefaultDibService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DibServiceTest {

    @InjectMocks
    private DefaultDibService dibService;

    @Mock
    private DibRepository dibRepository;

    @Test
    @DisplayName("찜 등록")
    void testCreateDib() throws UnAuthenticException, UnAuthorizationException {
        willDoNothing().given(dibRepository).createDib(anyLong());

        dibService.createDib(1L);

        then(dibRepository).should(times(1)).createDib(anyLong());
    }

    @Test
    @DisplayName("회원의 모든 찜 목록 조회")
    void testRetrieveDibs() throws UnAuthenticException, UnAuthorizationException {
        given(dibRepository.retrieveDibs()).willReturn(List.of());

        dibService.retrieveDibs();

        then(dibRepository).should(times(1)).retrieveDibs();
    }

    @Test
    @DisplayName("찜 삭제")
    void testDeleteDib() throws UnAuthenticException, UnAuthorizationException {
        willDoNothing().given(dibRepository).deleteDib(anyLong());

        dibService.deleteDib(1L);

        then(dibRepository).should(times(1)).deleteDib(anyLong());
    }

}
