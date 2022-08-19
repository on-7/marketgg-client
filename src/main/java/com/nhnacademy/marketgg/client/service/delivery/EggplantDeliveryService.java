package com.nhnacademy.marketgg.client.service.delivery;

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

    @Override
    public void updateDeliveryInfoStatus(final DeliveryInfoStatusRequestDto deliveryInfoStatusRequestDto) {
        deliveryRepository.updateDeliveryInfoStatus(deliveryInfoStatusRequestDto);
    }

}
