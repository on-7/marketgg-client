package com.nhnacademy.marketgg.client.repository.delivery;

import com.nhnacademy.marketgg.client.dto.request.DeliveryInfoStatusRequestDto;

public interface DeliveryRepository {
    void updateDeliveryInfoStatus(final DeliveryInfoStatusRequestDto deliveryInfoStatusRequestDto);
}
