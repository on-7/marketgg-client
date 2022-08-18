package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.dto.request.EmailRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberSignupRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberUpdateToAuth;
import com.nhnacademy.marketgg.client.dto.response.EmailExistResponse;
import com.nhnacademy.marketgg.client.dto.response.EmailUseResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import java.time.LocalDateTime;

/**
 * 회원 서비스 입니다.
 *
 * @version 1.0.0
 */
public interface MemberService {

    void doSignup(MemberSignupRequest memberSignupRequest) throws UnAuthenticException, UnAuthorizationException;

    EmailExistResponse checkEmail(EmailRequest emailRequest);

    EmailUseResponse useEmail(EmailRequest emailRequest);

    /**
     * 회원의 GG Pass 갱신일시를 반환하는 메소드입니다.
     *
     * @return 회원의 갱신일시를 반환합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 박세완
     * @since 1.0.0
     */
    LocalDateTime retrievePassUpdatedAt() throws UnAuthenticException, UnAuthorizationException;

    /**
     * 회원의 GG Pass 구독처리를 위한 메소드입니다.
     *
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 박세완
     * @since 1.0.0
     */
    void subscribePass() throws UnAuthenticException, UnAuthorizationException;

    /**
     * 회원의 GG Pass 구독해지처리를 위한 메소드입니다.
     *
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 박세완
     * @since 1.0.0
     */
    void withdrawPass() throws UnAuthenticException, UnAuthorizationException;

    /**
     * 지정한 회원의 정보 수정을 위한 메소드입니다.
     *
     * @param memberUpdateToAuth - 회원이 입력한 수정정보입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     */
    void update(final MemberUpdateToAuth memberUpdateToAuth, final String sessionId)
            throws UnAuthenticException, UnAuthorizationException;

    void withdraw(final String sessionId) throws UnAuthenticException, UnAuthorizationException;
}
