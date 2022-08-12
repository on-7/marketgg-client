package com.nhnacademy.marketgg.client.repository;

import com.nhnacademy.marketgg.client.dto.request.MemberSignupToShopMember;
import com.nhnacademy.marketgg.client.dto.request.MemberWithdrawRequest;
import com.nhnacademy.marketgg.client.dto.response.DeliveryAddressResponse;
import com.nhnacademy.marketgg.client.dto.response.MemberUpdateToAuthResponse;
import java.time.LocalDateTime;
import java.util.List;

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
     * @author 박세완
     * @since 1.0.0
     */
    LocalDateTime retrievePassUpdatedAt();

    /**
     * 지정한 회원의 GG PASS 구독처리 메소드입니다.
     *
     * @author 박세완
     * @since 1.0.0
     */
    void subscribePass();

    /**
     * 지정한 회원의 GG PASS 구독해지처리 메소드입니다.
     *
     * @author 박세완
     * @since 1.0.0
     */
    void withdrawPass();

    /**
     * 회원가입 메소드입니다.
     *
     * @param signupRequestToShopMember - Shop 서버에 저장될 회원의 정보를 담은 객체입니다.
     * @author 김훈민
     * @since 1.0.0
     */
    void signup(final MemberSignupToShopMember signupRequestToShopMember);

    /**
     * 회원탈퇴 메소드입니다.
     *
     * @param deletedAt - shop 에 저장되어있는 회원의 삭제 시간을 담고있는 객체 입니다.
     * @author 김훈민
     * @since 1.0.0
     */
    void withdraw(final MemberWithdrawRequest deletedAt);

    void update(final MemberUpdateToAuthResponse memberUpdateToAuthResponse, final String sessionId);

    /**
     * 회원이 가지고 있는 전체 배송지 조회 메소드 입니다.
     *
     * @return 회원 전체 배송지
     * @author 김훈민
     * @since 1.0.0
     */
    List<DeliveryAddressResponse> retrieveDeliveryAddresses();
}
