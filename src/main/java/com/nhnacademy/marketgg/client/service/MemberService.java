package com.nhnacademy.marketgg.client.service;

/**
 * 회원 서비스 입니다.
 *
 * @version 1.0.0
 */
public interface MemberService {

    /**
     * 지정한 회원의 GG Pass 갱신일시와 현재 일시를 비교하는 메소드입니다.
     *
     * @param id - 갱신일시를 확인할 회원의 식별번호입니다.
     * @return 회원의 갱신일시가 현재 시간보다 적다면 true, 많다면 false 를 반환합니다.
     * @since 1.0.0
     */
    Boolean checkPassUpdatedAt(final Long id);

    /**
     * 지정한 회원의 GG Pass 구독처리를 위한 메소드입니다.
     *
     * @param id - GG Pass 구독을 진행 할 회원의 식별번호입니다.
     * @since 1.0.0
     */
    void subscribePass(final Long id);

    /**
     * 지정한 회원의 GG Pass 구독해지처리를 위한 메소드입니다.
     *
     * @param id - GG Pass 구독해지를 진행 할 회원의 식별번호입니다.
     * @since 1.0.0
     */
    void withdrawPass(final Long id);

}
