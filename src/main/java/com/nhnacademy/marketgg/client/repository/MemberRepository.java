package com.nhnacademy.marketgg.client.repository;

import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.request.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.DeliveryAddressUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.SignupRequest;
import com.nhnacademy.marketgg.client.dto.response.DeliveryAddressResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import java.util.List;

/**
 * 회원 Repository 입니다.
 *
 * @version 1.0.0
 */
public interface MemberRepository {

    /**
     * 회원가입 메소드입니다.
     *
     * @param signupRequest - Shop 서버에 저장될 회원의 정보를 담은 객체입니다.
     * @author 김훈민
     * @since 1.0.0
     */
    void signup(final SignupRequest signupRequest)
        throws UnAuthenticException, UnAuthorizationException;

    /**
     * 회원탈퇴 메소드입니다.
     *
     * @author 김훈민
     * @since 1.0.0
     */
    void withdraw() throws UnAuthenticException, UnAuthorizationException;

    void update(final MemberUpdateRequest memberUpdateRequest, final MemberInfo memberInfo)
        throws UnAuthenticException, UnAuthorizationException;

    /**
     * 회원이 가지고 있는 전체 배송지 조회 메소드 입니다.
     *
     * @return 회원 전체 배송지
     * @author 김훈민
     * @since 1.0.0
     */
    List<DeliveryAddressResponse> retrieveDeliveryAddresses()
        throws UnAuthenticException, UnAuthorizationException;

    void createDeliveryAddress(final DeliveryAddressCreateRequest addressRequest)
        throws UnAuthenticException, UnAuthorizationException;

    void updateDeliveryAddress(final DeliveryAddressUpdateRequest updateRequest)
        throws UnAuthenticException, UnAuthorizationException;

    void deleteDeliveryAddress(final Long deliveryAddressId)
        throws UnAuthenticException, UnAuthorizationException;

}
