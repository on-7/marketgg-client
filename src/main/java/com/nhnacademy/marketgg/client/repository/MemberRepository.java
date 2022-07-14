package com.nhnacademy.marketgg.client.repository;

/**
 * 회원 Repository 입니다.
 *
 * @version 1.0.0
 */
public interface MemberRepository {

    /**
     * 지정한 회원의 GG PASS 갱신일시를 현재일시와 비교처리하는 메소드입니다.
     *
     * @param id - 갱신일시와 현재일시를 비교 할 회원의 식별번호입니다.
     * @return 회원의 갱신일시가 현재일시보다 빠를 시 False, 늦을 시 True 를 반환
     * @since 1.0.0
     */
    Boolean checkPassUpdatedAt(final Long id);

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
