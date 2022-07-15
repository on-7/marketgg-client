package com.nhnacademy.marketgg.client.service;

import java.time.LocalDateTime;

/**
 * 회원 서비스 입니다.
 *
 * @version 1.0.0
 */
public interface MemberService {

    /**
     * 지정한 회원의 GG Pass 갱신일시를 반환하는 메소드입니다.
     *
     * @param id - 갱신일시를 반환할 회원의 식별번호입니다.
     * @return 회원의 갱신일시를 반환합니다.
     * @since 1.0.0
     */
    LocalDateTime retrievePassUpdatedAt(final Long id);

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
