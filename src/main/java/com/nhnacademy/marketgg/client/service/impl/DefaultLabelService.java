package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.adapter.LabelAdapter;
import com.nhnacademy.marketgg.client.dto.LabelRegisterRequest;
import com.nhnacademy.marketgg.client.dto.LabelRetrieveResponse;
import com.nhnacademy.marketgg.client.service.LabelService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultLabelService implements LabelService {

    private final LabelAdapter labelAdapter;

    @Override
    public void createLabel(final LabelRegisterRequest labelRequest)
            throws JsonProcessingException {

        labelAdapter.createLabel(labelRequest);
    }

    @Override
    public List<LabelRetrieveResponse> retrieveLabels() {
        return labelAdapter.retrieveResponse();
    }

    @Override
    public void deleteLabel(final Long id) {
        labelAdapter.deleteLabel(id);
    }
}
