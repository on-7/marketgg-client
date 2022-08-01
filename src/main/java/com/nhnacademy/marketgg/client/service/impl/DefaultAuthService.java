package com.nhnacademy.marketgg.client.service.impl;

import com.nhnacademy.marketgg.client.dto.request.LoginRequest;
import com.nhnacademy.marketgg.client.repository.AuthRepository;
import com.nhnacademy.marketgg.client.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void doLogin(final LoginRequest loginRequest, final String sessionId) {
        loginRequest.encodePassword(passwordEncoder);
        authRepository.doLogin(loginRequest, sessionId);
    }

    @Override
    public void logout(String sessionId) {
        authRepository.logout(sessionId);
    }

}
