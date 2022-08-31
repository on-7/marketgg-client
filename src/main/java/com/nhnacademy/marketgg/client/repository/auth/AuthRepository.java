package com.nhnacademy.marketgg.client.repository.auth;

import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.common.MemberInfo;
import com.nhnacademy.marketgg.client.dto.member.LoginRequest;
import org.springframework.http.HttpHeaders;
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
     */
    ResponseEntity<CommonResult<String>> logout();

    /**
     * 회원 정보를 요청합니다.
     *
     * @return 회원정보
     */
    ResponseEntity<CommonResult<MemberInfo>> getMemberInfo();

    /**
     * 회원 정보를 요청합니다.
     *
     * @return 회원정보
     */
    ResponseEntity<CommonResult<MemberInfo>> getMemberInfo(HttpHeaders httpHeaders);

}
