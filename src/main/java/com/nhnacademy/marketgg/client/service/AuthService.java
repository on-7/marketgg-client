package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.dto.request.LoginRequest;

/**
 * 인증 관련 비즈니스 로직을 처리합니다.
 *
 * @version 1.0.0
 */
public interface AuthService {

    /**
     * 사용자가 로그인 요청 시 동작하는 메서드입니다.
     *
     * @param loginRequest - 사용자가 입력한 이메일, 비밀번호입니다.
     * @param sessionId - 사용자가 로그인 시 접속한 세션 아이디입니다.
     */
    void doLogin(final LoginRequest loginRequest, final String sessionId);

    /**
     * 사용자가 로그아웃 요청 시 동작하는 메서드입니다.
     *
     * @param sessionId Redis 에 저장되어있는 JWT 에 접근하기 위한 Session ID 입니다.
     */
    void logout(String sessionId);

}
