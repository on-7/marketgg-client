package com.nhnacademy.marketgg.client.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.LabelRegisterRequest;
import com.nhnacademy.marketgg.client.repository.LabelRepository;
import com.nhnacademy.marketgg.client.service.impl.DefaultLabelService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LabelServiceTest {

    @InjectMocks
    private DefaultLabelService labelService;

    @Mock
    private LabelRepository labelRepository;

    @Test
    @DisplayName("라벨 등록")
    void testCreateLabel() throws JsonProcessingException {
        LabelRegisterRequest labelRegisterRequest = new LabelRegisterRequest("labelName");

        willDoNothing().given(labelRepository).createLabel(any(LabelRegisterRequest.class));

        labelService.createLabel(labelRegisterRequest);

        then(labelRepository).should(times(1)).createLabel(any(LabelRegisterRequest.class));
    }

    @Test
    @DisplayName("라벨 목록 조회")
    void testRetrieveLabels() {
        given(labelRepository.retrieveResponse()).willReturn(List.of());

        labelService.retrieveLabels();

        then(labelRepository).should(times(1)).retrieveResponse();
    }

    @Test
    @DisplayName("라벨 삭제")
    void testDeleteLabel() {
        willDoNothing().given(labelRepository).deleteLabel(anyLong());

        labelService.deleteLabel(1L);

        then(labelRepository).should(times(1)).deleteLabel(anyLong());
    }

}
