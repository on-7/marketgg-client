package com.nhnacademy.marketgg.client.util;

/**
 * 마이크로서비스에 API 요청을 보낼 때 필요한 URL 정보를 제공하는 인터페이스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
public interface GgUrlUtils {

    int WEEK_SECOND = 60 * 60 * 24 * 7;

    String GATEWAY_HORT_URL = "http://127.0.0.1:6060";

    String SHOP_SERVICE_PREFIX_V1 = "/shop/v1";
    String AUTH_SERVICE_PREFIX_V1 = "/auth/v1";

    String ORDERS_PATH_PREFIX = "/orders";
    String REDIRECT_TO_INDEX = "redirect:/";


}
