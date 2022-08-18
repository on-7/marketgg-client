package com.nhnacademy.marketgg.client.dto.response.common;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.ShopResult;
import com.nhnacademy.marketgg.client.exception.ClientException;
import com.nhnacademy.marketgg.client.exception.NotFoundException;
import com.nhnacademy.marketgg.client.exception.ServerException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import java.util.List;
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
     * List 타입으로 응답받을 때 사용됩니다.
     *
     * @param response - 응답결과
     * @param mapper   - Json 을 자바로 매핑할 때 사용됩니다.
     * @param <T>      - 응답 결과의 타입
     * @return - 응답 결과
     * @throws JsonProcessingException - Json 을 파싱 시 발생할 수 있는 예외입니다.
     */
    public static <T> List<T> getListData(String response, ObjectMapper mapper) throws JsonProcessingException {
        ListResponse<T> listResponse = mapper.readValue(response, new TypeReference<>() {
        });

        return listResponse.getData();
    }

    /**
     * 단일 타입으로 응답받을 때 사용됩니다.
     *
     * @param response - 응답결과
     * @param mapper   - Json 을 자바로 매핑할 때 사용됩니다.
     * @param <T>      - 응답 결과의 타입
     * @return - 응답 결과
     * @throws JsonProcessingException - Json 을 파싱 시 발생할 수 있는 예외입니다.
     */
    public static <T> T getData(String response, ObjectMapper mapper) throws JsonProcessingException {
        SingleResponse<T> singleResponse = mapper.readValue(response, new TypeReference<>() {
        });

        return singleResponse.getData();
    }

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
            if (Objects.equals(httpStatus, UNAUTHORIZED)) {
                throw new UnAuthenticException();
            } else if (Objects.equals(httpStatus, FORBIDDEN)) {
                throw new UnAuthorizationException();
            } else if (Objects.equals(httpStatus, NOT_FOUND)) {
                throw new NotFoundException(errorEntity.getMessage());
            } else if (httpStatus.is4xxClientError()) {
                throw new ClientException(errorEntity.getMessage());
            } else if (httpStatus.is5xxServerError()) {
                throw new ServerException();
            }
        }
    }

    public static <T> void checkErrorForResponse(final ResponseEntity<ShopResult<T>> response)
            throws UnAuthenticException, UnAuthorizationException {
        HttpStatus httpStatus = response.getStatusCode();

        if (httpStatus.is4xxClientError() || httpStatus.is5xxServerError()) {
            if (Objects.equals(httpStatus, UNAUTHORIZED)) {
                throw new UnAuthenticException();
            } else if (Objects.equals(httpStatus, FORBIDDEN)) {
                throw new UnAuthorizationException();
            } else if (Objects.equals(httpStatus, NOT_FOUND)) {
                throw new NotFoundException(Objects.requireNonNull(response.getBody()).getError().getMessage());
            } else if (httpStatus.is4xxClientError()) {
                throw new ClientException(Objects.requireNonNull(response.getBody()).getError().getMessage());
            } else {
                throw new ServerException();
            }
        }
    }

}
