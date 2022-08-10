package com.nhnacademy.marketgg.client.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import com.nhnacademy.marketgg.client.repository.MemberRepository;
import com.nhnacademy.marketgg.client.service.impl.DefaultMemberService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private DefaultMemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 GG 패스 갱신일 조회")
    void retrievePassUpdatedAt() {
        given(memberRepository.retrievePassUpdatedAt()).willReturn(LocalDateTime.now());

        LocalDateTime memberUpdateDate = memberService.retrievePassUpdatedAt();

        assertThat(memberUpdateDate.isEqual(LocalDateTime.now())).isFalse();
    }

    @Test
    @DisplayName("회원 GG 패스 구독")
    void subscribePass() {
        willDoNothing().given(memberRepository).subscribePass();

        memberService.subscribePass();

        then(memberRepository).should(times(1)).subscribePass();
    }

    @Test
    @DisplayName("회원 GG 패스 해지")
    void withdrawPass() {
        willDoNothing().given(memberRepository).withdrawPass();

        memberService.withdrawPass();

        then(memberRepository).should(times(1)).withdrawPass();
    }

}