package com.nhnacademy.marketgg.client.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.LabelRegisterRequest;
import com.nhnacademy.marketgg.client.dto.LabelRetrieveResponse;
import java.util.List;

public interface LabelAdapter {

    void createLabel(final LabelRegisterRequest labelRequest) throws JsonProcessingException;

    List<LabelRetrieveResponse> retrieveResponse();

    void deleteLabel(final Long id);
}
