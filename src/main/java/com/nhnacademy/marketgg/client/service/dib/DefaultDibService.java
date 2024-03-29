package com.nhnacademy.marketgg.client.service.dib;

import com.nhnacademy.marketgg.client.dto.dib.DibRetrieveResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.dib.DibRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultDibService implements DibService {

    private final DibRepository dibRepository;

    @Override
    public void createDib(final Long productId) throws UnAuthenticException, UnAuthorizationException {
        dibRepository.createDib(productId);
    }

    @Override
    public List<DibRetrieveResponse> retrieveDibs() throws UnAuthenticException, UnAuthorizationException {
        return dibRepository.retrieveDibs();
    }

    @Override
    public void deleteDib(final Long productId) throws UnAuthenticException, UnAuthorizationException {
        dibRepository.deleteDib(productId);
    }
}
