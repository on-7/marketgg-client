package com.nhnacademy.marketgg.client.service.delivery;

import com.nhnacademy.marketgg.client.dto.request.DeliveryInfoStatusRequestDto;

/**
 * 배송 서버에서 전송된 데이터의 로직처리를 위한 인터페이스 입니다.
 *
 * @author 김훈민
 * @version 1.0.0
 */
public interface DeliveryService {
    void updateDeliveryInfoStatus(final DeliveryInfoStatusRequestDto deliveryInfoStatusRequestDto);

}
