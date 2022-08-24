package com.nhnacademy.marketgg.client.service.impl;

import com.nhnacademy.marketgg.client.dto.request.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.DeliveryAddressUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.EmailRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberUpdateToAuth;
import com.nhnacademy.marketgg.client.dto.request.MemberWithdrawRequest;
import com.nhnacademy.marketgg.client.dto.request.SignupRequest;
import com.nhnacademy.marketgg.client.dto.response.DeliveryAddressResponse;
import com.nhnacademy.marketgg.client.dto.response.EmailExistResponse;
import com.nhnacademy.marketgg.client.dto.response.EmailUseResponse;
import com.nhnacademy.marketgg.client.dto.response.MemberUpdateToAuthResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.MemberRepository;
import com.nhnacademy.marketgg.client.repository.auth.MemberInfoRepository;
import com.nhnacademy.marketgg.client.service.MemberService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 회원정보를 담당하는 처리하는 서비스 클래스 입니다.
 *
 * @author 김훈민, 박세완
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class DefaultMemberService implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void update(final MemberUpdateToAuth memberUpdateToAuth, final String sessionId)
        throws UnAuthenticException, UnAuthorizationException {

        MemberUpdateToAuthResponse updateData = memberInfoRepository.update(memberUpdateToAuth, sessionId);
        memberRepository.update(updateData, sessionId);
    }

    @Override
    public void withdraw(final String sessionId) throws UnAuthenticException, UnAuthorizationException {
        MemberWithdrawRequest withdrawRequest = new MemberWithdrawRequest(LocalDateTime.now());
        memberInfoRepository.withdraw(withdrawRequest, sessionId);
        memberRepository.withdraw(withdrawRequest);
    }

    @Override
    public List<DeliveryAddressResponse> retrieveDeliveryAddresses()
        throws UnAuthenticException, UnAuthorizationException {
        return memberRepository.retrieveDeliveryAddresses();
    }

    @Override
    public void createDeliveryAddress(final DeliveryAddressCreateRequest addressRequest)
        throws UnAuthenticException, UnAuthorizationException {
        memberRepository.createDeliveryAddress(addressRequest);
    }

    @Override
    public void updateDeliveryAddress(final DeliveryAddressUpdateRequest updateRequest)
        throws UnAuthenticException, UnAuthorizationException {
        memberRepository.updateDeliveryAddress(updateRequest);
    }

    @Override
    public void deleteDeliveryAddress(final Long deliveryAddressId)
        throws UnAuthenticException, UnAuthorizationException {
        memberRepository.deleteDeliveryAddress(deliveryAddressId);
    }

    /**
     * {@inheritDoc}
     *
     * @param signupRequest - 회원가입 폼에서 입력한 데이터 정보 입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     */
    @Override
    public void doSignup(final SignupRequest signupRequest)
        throws UnAuthenticException, UnAuthorizationException {
        memberRepository.signup(signupRequest);
    }

    @Override
    public EmailExistResponse checkEmail(final EmailRequest emailRequest) {
        return memberInfoRepository.checkEmail(emailRequest);
    }

    @Override
    public EmailUseResponse useEmail(final EmailRequest emailRequest) {

        EmailUseResponse emailUseResponse = memberInfoRepository.useEmail(emailRequest);
        Map<String, EmailUseResponse> useEmailResultMap = new HashMap<>();
        useEmailResultMap.put("isUseEmail", emailUseResponse);

        return useEmailResultMap.get("isUeEmail");
    }

}
