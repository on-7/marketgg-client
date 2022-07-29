package com.nhnacademy.marketgg.client.dto.response.common;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseUtils {

    public static <T> List<T> getListData(String response, ObjectMapper mapper) throws JsonProcessingException {
        ListResponse<T> listResponse = mapper.readValue(response, new TypeReference<>() {
        });

        return listResponse.getData();
    }

    public static <T> T getData(String response, ObjectMapper mapper) throws JsonProcessingException {
        SingleResponse<T> singleResponse = mapper.readValue(response, new TypeReference<>() {
        });

        return singleResponse.getData();
    }

    public static void checkError(ResponseEntity<String> response, ObjectMapper mapper)
        throws JsonProcessingException, UnAuthorizationException, UnAuthenticException {

        HttpStatus httpStatus = response.getStatusCode();

        if (httpStatus.is4xxClientError() || httpStatus.is5xxServerError()) {
            ErrorEntity errorEntity = mapper.readValue(response.getBody(), ErrorEntity.class);
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

}
