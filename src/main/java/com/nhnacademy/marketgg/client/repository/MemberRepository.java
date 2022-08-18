package com.nhnacademy.marketgg.client.repository;

import com.nhnacademy.marketgg.client.dto.request.MemberSignupToShopMember;
import com.nhnacademy.marketgg.client.dto.request.MemberWithdrawRequest;
import com.nhnacademy.marketgg.client.dto.response.MemberUpdateToAuthResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import java.time.LocalDateTime;

/**
 * 회원 Repository 입니다.
 *
 * @version 1.0.0
 */
public interface MemberRepository {

    /**
     * 지정한 회원의 GG PASS 갱신일시를 반환하는 메소드입니다.
     *
     * @return 회원의 갱신일시를 반환합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 박세완
     * @since 1.0.0
     */
    LocalDateTime retrievePassUpdatedAt() throws UnAuthenticException, UnAuthorizationException;

    /**
     * 지정한 회원의 GG PASS 구독처리 메소드입니다.
     *
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 박세완
     * @since 1.0.0
     */
    void subscribePass() throws UnAuthenticException, UnAuthorizationException;

    /**
     * 지정한 회원의 GG PASS 구독해지처리 메소드입니다.
     *
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 박세완
     * @since 1.0.0
     */
    void withdrawPass() throws UnAuthenticException, UnAuthorizationException;

    /**
     * 회원가입 메소드입니다.
     *
     * @param signupRequestToShopMember - Shop 서버에 저장될 회원의 정보를 담은 객체입니다.
     */
    void signup(final MemberSignupToShopMember signupRequestToShopMember)
            throws UnAuthenticException, UnAuthorizationException;

    void withdraw(final MemberWithdrawRequest deletedAt) throws UnAuthenticException, UnAuthorizationException;

    void update(final MemberUpdateToAuthResponse memberUpdateToAuthResponse, final String sessionId)
            throws UnAuthenticException, UnAuthorizationException;
}
