package com.nhnacademy.marketgg.client.repository;

import java.time.LocalDateTime;

/**
 * 회원 Repository 입니다.
 *
 * @version 1.0.0
 */
public interface MemberRepository {

    /**
     * 지정한 회원의 GG PASS 갱신일시를 반환하는 메소드입니다.
     *
     * @param id - 갱신일시를 반환할 회원의 식별번호입니다.
     * @return 회원의 갱신일시를 반환합니다.
     * @since 1.0.0
     */
    LocalDateTime retrievePassUpdatedAt(final Long id);

    /**
     * 지정한 회원의 GG PASS 구독처리 메소드입니다.
     *
     * @param id - 구독 할 회원의 식별번호입니다.
     * @since 1.0.0
     */
    void subscribePass(final Long id);

    /**
     * 지정한 회원의 GG PASS 구독해지처리 메소드입니다.
     *
     * @param id - 구독해지 할 회원의 식별번호입니다.
     * @since 1.0.0
     */
    void withdrawPass(final Long id);

}
