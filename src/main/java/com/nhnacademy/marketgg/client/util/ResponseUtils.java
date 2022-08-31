package com.nhnacademy.marketgg.client.util;

import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.common.ErrorEntity;
import com.nhnacademy.marketgg.client.exception.ClientException;
import com.nhnacademy.marketgg.client.exception.NotFoundException;
import com.nhnacademy.marketgg.client.exception.ServerException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * RestTemplate 으로 응답받은 값을 처리할 때 사용되는 Util 클래스입니다.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseUtils {

    /**
     * 예외를 확인합니다.
     *
     * @param response - 응답결과
     */
    public static <T> void checkError(ResponseEntity<CommonResult<T>> response)
            throws UnAuthorizationException, UnAuthenticException {

        HttpStatus httpStatus = response.getStatusCode();

        if (httpStatus.is4xxClientError() || httpStatus.is5xxServerError()) {
            ErrorEntity errorEntity = Objects.requireNonNull(response.getBody()).getError();
            switch (httpStatus) {
                case UNAUTHORIZED:
                    throw new UnAuthenticException();
                case FORBIDDEN:
                    throw new UnAuthorizationException();
                case NOT_FOUND:
                    throw new NotFoundException(errorEntity.getMessage());
                default:
                    if (httpStatus.is4xxClientError()) {
                        throw new ClientException(errorEntity.getMessage());
                    } else {
                        throw new ServerException();
                    }
            }
        }

    }

}
