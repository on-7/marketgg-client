package com.nhnacademy.marketgg.client.repository;

public interface MemberRepository {

    Boolean checkPassUpdatedAt(final Long id);

    void subscribePass(final Long id);

    void withdrawPass(final Long id);

}
