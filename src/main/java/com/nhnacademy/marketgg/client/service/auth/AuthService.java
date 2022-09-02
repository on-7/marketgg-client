package com.nhnacademy.marketgg.client.service.auth;

import com.nhnacademy.marketgg.client.dto.member.LoginRequest;
import com.nhnacademy.marketgg.client.util.LoginStatus;

/**
 * 인증 관련 비즈니스 로직을 처리합니다.
 *
 * @author 윤동열
 * @version 1.0.0
 */
public interface AuthService {

    /**
     * 사용자가 로그인 요청 시 동작하는 메서드입니다.
     *
     * @param loginRequest - 사용자가 입력한 이메일, 비밀번호입니다.
     * @param sessionId    - 사용자가 로그인 시 접속한 세션 아이디입니다.
     */
    LoginStatus doLogin(final LoginRequest loginRequest, final String sessionId);

    /**
     * 사용자가 로그아웃 요청 시 동작하는 메서드입니다.
     */
    void logout();

}
