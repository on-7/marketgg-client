package com.nhnacademy.marketgg.client.repository.auth;

import com.nhnacademy.marketgg.client.dto.request.LoginRequest;
import com.nhnacademy.marketgg.client.dto.response.common.CommonResult;
import org.springframework.http.ResponseEntity;

/**
 * Client Server 와 Auth Server 사이에서 데이터를 주고 받습니다.
 *
 * @author 윤동열
 */
public interface AuthRepository {

    /**
     * 사용자가 로그인 요청 시 인증서버에 로그인 요청을 전송합니다.
     *
     * @param loginRequest 사용자의 이메일, 비밀번호
     * @param sessionId    JWT 를 Redis 에 넣기 위해 필요한 사용자의 Session ID
     */
    ResponseEntity<Void> doLogin(LoginRequest loginRequest, String sessionId);

    /**
     * 사용자가 로그아웃 요청 시 인증서버에 로그아웃 요청을 전송합니다.
     *
     * @param sessionId Redis 에 저장된 JWT 에 접근하기 위해 사용됩니다.
     */
    ResponseEntity<CommonResult<String>> logout(String sessionId);

}
