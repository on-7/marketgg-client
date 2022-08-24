package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.dto.request.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.DeliveryAddressUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.EmailRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberUpdateToAuth;
import com.nhnacademy.marketgg.client.dto.request.SignupRequest;
import com.nhnacademy.marketgg.client.dto.response.DeliveryAddressResponse;
import com.nhnacademy.marketgg.client.dto.response.EmailExistResponse;
import com.nhnacademy.marketgg.client.dto.response.EmailUseResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import java.util.List;

/**
 * 회원 서비스 입니다.
 *
 * @version 1.0.0
 */
public interface MemberService {

    /**
     * 회원가입을 위한 메소드 입니다.
     *
     * @param signupRequest - 회원가입 폼에서 입력한 데이터 정보 입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     */
    void doSignup(final SignupRequest signupRequest) throws UnAuthenticException, UnAuthorizationException;

    EmailExistResponse checkEmail(EmailRequest emailRequest);

    EmailUseResponse useEmail(EmailRequest emailRequest);

    /**
     * 지정한 회원의 정보 수정을 위한 메소드입니다.
     *
     * @param memberUpdateToAuth - 회원이 입력한 수정정보입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     */
    void update(final MemberUpdateToAuth memberUpdateToAuth, final String sessionId)
        throws UnAuthenticException, UnAuthorizationException;

    /**
     * 회원이 보유한 배송지 목록 리스트를 보여줍니다.
     *
     * @return 회원이 보유한 배송지 목록 리스트 입니다.
     * @author 김훈민
     * @since 1.0.0
     */
    List<DeliveryAddressResponse> retrieveDeliveryAddresses()
        throws UnAuthenticException, UnAuthorizationException;

    /**
     * 회원이 회원가입시 입력했던 배송지외에 배송지를 추가하는 메소드 입니다.
     *
     * @param addressRequest - 추가하는 배송지의 정보를 담고있는 DTO 입니다.
     * @author 김훈민
     * @since 1.0.0
     */
    void createDeliveryAddress(final DeliveryAddressCreateRequest addressRequest)
        throws UnAuthenticException, UnAuthorizationException;

    /**
     * 회원이 가진 배송지 정보를 수정하는 메소드 입니다.
     *
     * @param updateRequest - 수정하는 배송지의 정보를 담고있는 DTO 입니다.
     * @author 김훈민
     * @since 1.0.0
     */
    void updateDeliveryAddress(final DeliveryAddressUpdateRequest updateRequest)
        throws UnAuthenticException, UnAuthorizationException;

    /**
     * 회원이 가진 배송지 정보를 삭제하는 메소드 입니다.
     *
     * @param deliveryAddressId - 삭제하는 배송지의 식별번호
     * @author 김훈민
     * @since 1.0.0
     */
    void deleteDeliveryAddress(final Long deliveryAddressId)
        throws UnAuthenticException, UnAuthorizationException;

    void withdraw(final String sessionId) throws UnAuthenticException, UnAuthorizationException;
}
