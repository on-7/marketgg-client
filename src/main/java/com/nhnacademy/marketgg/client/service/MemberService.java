package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.dto.request.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.EmailRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberSignupRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberUpdateToAuth;
import com.nhnacademy.marketgg.client.dto.response.DeliveryAddressResponse;
import com.nhnacademy.marketgg.client.dto.response.EmailExistResponse;
import com.nhnacademy.marketgg.client.dto.response.EmailUseResponse;
import java.time.LocalDateTime;
import java.util.List;

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
     * 회원의 GG Pass 갱신일시를 반환하는 메소드입니다.
     *
     * @return 회원의 갱신일시를 반환합니다.
     * @author 박세완
     * @since 1.0.0
     */
    LocalDateTime retrievePassUpdatedAt();

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

    /**
     * 회원이 보유한 배송지 목록 리스트를 보여줍니다.
     *
     * @return 회원이 보유한 배송지 목록 리스트 입니다.
     * @author 김훈민
     * @since 1.0.0
     */
    List<DeliveryAddressResponse> retrieveDeliveryAddresses();

    /**
     * 회원이 회원가입시 입력했던 배송지외에 배송지를 추가하는 메소드 입니다.
     *
     * @param addressRequest - 추가하는 배송지의 정보를 담고있는 DTO 입니다.
     * @author 김훈민
     * @since 1.0.0
     */
    void createDeliveryAddress(final DeliveryAddressCreateRequest addressRequest);
}
