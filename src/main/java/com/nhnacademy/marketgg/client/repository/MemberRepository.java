package com.nhnacademy.marketgg.client.repository;

import com.nhnacademy.marketgg.client.dto.request.MemberSignupToShopMember;
import com.nhnacademy.marketgg.client.dto.request.MemberWithdrawRequest;
import com.nhnacademy.marketgg.client.dto.response.MemberUpdateToAuthResponse;

/**
 * 회원 Repository 입니다.
 *
 * @version 1.0.0
 */
public interface MemberRepository {

    /**
     * 회원의 GG PASS 구독처리 메소드입니다.
     *
     * @author 박세완
     * @since 1.0.0
     */
    void subscribePass();

    /**
     * 회원의 GG PASS 구독해지처리 메소드입니다.
     *
     * @author 박세완
     * @since 1.0.0
     */
    void withdrawPass();

    /**
     * 회원가입 메소드입니다.
     *
     * @param signupRequestToShopMember - Shop 서버에 저장될 회원의 정보를 담은 객체입니다.
     */
    void signup(final MemberSignupToShopMember signupRequestToShopMember);

    void withdraw(final MemberWithdrawRequest deletedAt);

    void update(final MemberUpdateToAuthResponse memberUpdateToAuthResponse, final String sessionId);
}
