package com.nhnacademy.marketgg.client.service.delivery;

import com.nhnacademy.marketgg.client.dto.delivery.CreatedTrackingNoRequest;
import com.nhnacademy.marketgg.client.dto.delivery.DeliveryInfoStatusRequestDto;

/**
 * 배송 서버에서 전송된 데이터의 로직처리를 위한 인터페이스 입니다.
 *
 * @author 김훈민
 * @version 1.0.0
 */
public interface DeliveryService {

    /**
     * 배송 서버에서 배송정보가 바뀔시 Client 서버에서 요청을 받은뒤 로직을 처리합니다.
     *
     * @param deliveryInfoStatusRequestDto - 배송 서버에서 전송된 데이터 입니다.
     */
    void updateDeliveryInfoStatus(final DeliveryInfoStatusRequestDto deliveryInfoStatusRequestDto);

    /**
     * 배송 서버에서 운송장 번호를 만든뒤 Client 서버로 요청을 보냅니다.
     * 해당 메소드는 운송장 번호를 만들어 주는 로직을 처리하는 메소드 입니다.
     *
     * @param createdTrackingNoRequest - 배송 서버에서 전송된 데이터 입니다.
     */
    void createdTrackingNo(final CreatedTrackingNoRequest createdTrackingNoRequest);

}
