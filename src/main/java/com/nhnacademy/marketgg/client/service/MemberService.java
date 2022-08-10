package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.dto.request.EmailRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberSignupRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberUpdateToAuth;
import com.nhnacademy.marketgg.client.dto.response.EmailExistResponse;
import com.nhnacademy.marketgg.client.dto.response.EmailUseResponse;

/**
 * 회원 서비스 입니다.
 *
 * @version 1.0.0
 */
public interface MemberService {

    void doSignup(MemberSignupRequest memberSignupRequest);

    EmailExistResponse checkEmail(EmailRequest emailRequest);

    EmailUseResponse useEmail(EmailRequest emailRequest);

    /**
     * 회원의 GG Pass 구독처리를 위한 메소드입니다.
     *
     * @author 박세완
     * @since 1.0.0
     */
    void subscribePass();

    /**
     * 회원의 GG Pass 구독해지처리를 위한 메소드입니다.
     *
     * @author 박세완
     * @since 1.0.0
     */
    void withdrawPass();

    /**
     * 지정한 회원의 정보 수정을 위한 메소드입니다.
     *
     * @param memberUpdateToAuth - 회원이 입력한 수정정보입니다.
     */
    void update(final MemberUpdateToAuth memberUpdateToAuth, final String sessionId);

    void withdraw(final String sessionId);
}
