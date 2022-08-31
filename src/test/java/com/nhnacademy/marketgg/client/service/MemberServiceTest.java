package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.repository.member.MemberRepository;
import com.nhnacademy.marketgg.client.service.member.DefaultMemberService;
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

}
