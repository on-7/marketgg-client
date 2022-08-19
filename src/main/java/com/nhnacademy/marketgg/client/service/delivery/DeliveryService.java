package com.nhnacademy.marketgg.client.service.delivery;

import com.nhnacademy.marketgg.client.dto.request.DeliveryInfoStatusRequestDto;


public interface DeliveryService {
    void updateDeliveryInfoStatus(final DeliveryInfoStatusRequestDto deliveryInfoStatusRequestDto);

}
