package com.nhnacademy.marketgg.client.web.delivery;

import com.nhnacademy.marketgg.client.dto.delivery.CreatedTrackingNoRequest;
import com.nhnacademy.marketgg.client.dto.delivery.DeliveryInfoStatusRequestDto;
import com.nhnacademy.marketgg.client.service.delivery.DeliveryService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 배송 서버 요청을 처리하기 위한 클래스 입니다.
 *
 * @author 김훈민
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/eggplant")
public class DeliveryController {
    private final DeliveryService deliveryService;

    /**
     * 배송 서버에서 주문상태를 바꾸기 위한 GetMapping 메소드 입니다.
     */
    @GetMapping("/delivery-info")
    public void updateDeliveryInfoStatus(@RequestParam String orderNo,
                                         @RequestParam String status,
                                         @RequestParam(name = "arrivalTime", required = false)
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                         LocalDateTime arrivalTime) {

        deliveryService.updateDeliveryInfoStatus(DeliveryInfoStatusRequestDto.builder()
                                                                             .orderNo(orderNo)
                                                                             .status(status)
                                                                             .arrivalTime(arrivalTime)
                                                                             .build());
    }

    /**
     * 배송 서버에서 만들어진 운송장 번호를 받기 위한 GetMapping 메소드 입니다.
     */
    @GetMapping("/tracking-no/{trackingNo}")
    public void createdTrackingNo(@PathVariable String trackingNo,
                                  @RequestParam("orderNo") String orderNo) {
        deliveryService.createdTrackingNo(CreatedTrackingNoRequest.builder()
                                                                  .trackingNo(trackingNo)
                                                                  .orderNo(orderNo)
                                                                  .build());
    }

}
