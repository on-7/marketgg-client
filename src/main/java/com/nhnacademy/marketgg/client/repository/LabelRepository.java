package com.nhnacademy.marketgg.client.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.LabelRegisterRequest;
import com.nhnacademy.marketgg.client.dto.response.LabelRetrieveResponse;
import java.util.List;

public interface LabelRepository {

    void createLabel(final LabelRegisterRequest labelRequest) throws JsonProcessingException;

    List<LabelRetrieveResponse> retrieveResponse();

    void deleteLabel(final Long id);
}
