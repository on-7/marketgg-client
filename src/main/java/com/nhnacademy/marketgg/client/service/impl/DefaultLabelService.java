package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.LabelRegisterRequest;
import com.nhnacademy.marketgg.client.dto.response.LabelRetrieveResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.LabelRepository;
import com.nhnacademy.marketgg.client.service.LabelService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultLabelService implements LabelService {

    private final LabelRepository labelRepository;


    @Override
    public void createLabel(final LabelRegisterRequest labelRequest)
        throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        labelRepository.createLabel(labelRequest);
    }

    @Override
    public List<LabelRetrieveResponse> retrieveLabels() throws UnAuthenticException, UnAuthorizationException {
        return labelRepository.retrieveResponse();
    }

    @Override
    public void deleteLabel(final Long id) throws UnAuthenticException, UnAuthorizationException {
        labelRepository.deleteLabel(id);
    }
}
