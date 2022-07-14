package com.nhnacademy.marketgg.client.service.impl;

import com.nhnacademy.marketgg.client.repository.MemberRepository;
import com.nhnacademy.marketgg.client.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultMemberService implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Boolean checkPassUpdatedAt(final Long id) {
        return memberRepository.checkPassUpdatedAt(id);
    }

    @Override
    public void subscribePass(final Long id) {
        memberRepository.subscribePass(id);
    }

    @Override
    public void withdrawPass(final Long id) {
        memberRepository.withdrawPass(id);
    }

}
