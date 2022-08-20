package com.nhnacademy.marketgg.client.web.delivery;

import com.nhnacademy.marketgg.client.dto.request.DeliveryInfoStatusRequestDto;
import com.nhnacademy.marketgg.client.service.delivery.DeliveryService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 배송 서버 요청을 처리하기 위한 클래스 입니다.
 *
 * @author 김훈민
 * @version 1.0.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/eggplant-delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    /**
     * 배송 서버에서 주문상태를 바꾸기 위한 PatchMapping 메소드 입니다.
     *
     * @param deliveryInfoStatusRequestDto - 배송서버에서 전송된 데이터를 담고있는 객체 입니다.
     */
    @PatchMapping(name = "/delivery-info")
    public void updateDeliveryInfoStatus(@RequestBody @Valid final DeliveryInfoStatusRequestDto deliveryInfoStatusRequestDto) {
        deliveryService.updateDeliveryInfoStatus(deliveryInfoStatusRequestDto);
    }

}
