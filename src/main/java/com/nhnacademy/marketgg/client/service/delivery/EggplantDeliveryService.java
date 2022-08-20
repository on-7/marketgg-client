package com.nhnacademy.marketgg.client.service.delivery;

import com.nhnacademy.marketgg.client.dto.request.CreatedTrackingNoRequest;
import com.nhnacademy.marketgg.client.dto.request.DeliveryInfoStatusRequestDto;
import com.nhnacademy.marketgg.client.repository.delivery.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * {@inheritDoc}
 *
 * @author 김훈민
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class EggplantDeliveryService implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    /**
     * {@inheritDoc}
     *
     * @param deliveryInfoStatusRequestDto - 배송 서버에서 전송된 데이터 입니다.
     */
    @Override
    public void updateDeliveryInfoStatus(final DeliveryInfoStatusRequestDto deliveryInfoStatusRequestDto) {
        deliveryRepository.updateDeliveryInfoStatus(deliveryInfoStatusRequestDto);
    }

    /**
     * {@inheritDoc}
     *
     * @param createdTrackingNoRequest - 배송 서버에서 전송된 데이터 입니다.
     */
    @Override
    public void createdTrackingNo(final CreatedTrackingNoRequest createdTrackingNoRequest) {
        deliveryRepository.createdTrackingNo(createdTrackingNoRequest);
    }

}
