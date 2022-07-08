package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.LabelRegisterRequest;
import com.nhnacademy.marketgg.client.dto.LabelRetrieveResponse;

import java.util.List;

public interface LabelService {
    void createLabel(final LabelRegisterRequest labelRequest) throws JsonProcessingException;

    List<LabelRetrieveResponse> retrieveLabels();

    void deleteLabel(Long labelId);

}
