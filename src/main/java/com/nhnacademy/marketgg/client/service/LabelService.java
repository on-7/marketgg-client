package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.LabelRegisterRequest;

public interface LabelService {
    void createLabel(final LabelRegisterRequest labelRequest) throws JsonProcessingException;

    void deleteLabel(Long labelId);

}
