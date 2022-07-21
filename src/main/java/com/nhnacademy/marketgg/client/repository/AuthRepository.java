package com.nhnacademy.marketgg.client.repository;

import com.nhnacademy.marketgg.client.dto.request.LoginRequest;

public interface AuthRepository {

    void doLogin(LoginRequest loginRequest, String sessionId);

}
