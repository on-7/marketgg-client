package com.nhnacademy.marketgg.client.repository.delivery;

import com.nhnacademy.marketgg.client.dto.request.DeliveryInfoStatusRequestDto;

/**
 * Shop Server 로 Delivery 서버 데이터를 전송하기 위한 인터페이스 입니다.
 *
 * @author 김훈민
 * @version 1.0.0
 */
public interface DeliveryRepository {

    /**
     * 배송 서버에서 전송된 배송 상태를 주문 정보에 넣어주기 위한 메소드 입니다.
     *
     * @param deliveryInfoStatusRequestDto - 배송서버에서 전송된 데이터 객체입니다.
     */
    void updateDeliveryInfoStatus(final DeliveryInfoStatusRequestDto deliveryInfoStatusRequestDto);

}
