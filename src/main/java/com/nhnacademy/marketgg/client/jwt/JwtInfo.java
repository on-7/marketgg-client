package com.nhnacademy.marketgg.client.jwt;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.common.MemberInfo;
import com.nhnacademy.marketgg.client.exception.ServerException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Slf4j
@Getter
public class JwtInfo implements Serializable {

    private static final long serialVersionUID = 1905122041950251207L;

    public static final String JWT_REDIS_KEY = "JWT";
    public static final String JWT_EXPIRE = "JWT-Expire";
    public static final String SESSION_ID = "SESSION-ID";
    public static final String BEARER = "Bearer ";
    public static final int BEARER_LENGTH = 7;

    private final String jwt;
    private final LocalDateTime jwtExpireDate;
    private final List<GrantedAuthority> authorities;

    private String email;
    private String name;
    private String phoneNumber;
    private String memberGrade;
    private Character gender;
    private LocalDate birthDay;

    public JwtInfo(String jwt, String jwtExpireDate) throws JsonProcessingException {
        this.jwt = jwt;
        this.authorities = parseJwt(jwt).stream()
                                        .map(SimpleGrantedAuthority::new)
                                        .collect(toList());
        try {
            this.jwtExpireDate = LocalDateTime.parse(jwtExpireDate);
        } catch (Exception e) {
            throw new ServerException();
        }
    }

    public JwtInfo(String jwt, String jwtExpireDate, MemberInfo memberInfo) throws JsonProcessingException {
        this(jwt, jwtExpireDate);
        this.email = memberInfo.getEmail();
        this.name = memberInfo.getName();
        this.phoneNumber = memberInfo.getPhoneNumber();
        this.memberGrade = memberInfo.getMemberGrade();
        this.gender = memberInfo.getGender();
        this.birthDay = memberInfo.getBirthDay();
    }

    public MemberInfo getMemberInfo() {
        return new MemberInfo(this.email, this.name, this.phoneNumber, this.memberGrade, this.gender, this.birthDay);
    }

    public static void saveJwt(RedisTemplate<String, Object> redisTemplate, MemberInfo memberInfo,
                               String sessionId, String jwt, String expiredAt) {
        JwtInfo jwtInfo;
        try {
            jwtInfo = new JwtInfo(jwt, expiredAt, memberInfo);
        } catch (JsonProcessingException e) {
            log.error(e.toString());
            throw new ServerException();
        }

        LocalDateTime expire = jwtInfo.getJwtExpireDate()
                                      .plus(6, DAYS)
                                      .plus(30, MINUTES);

        redisTemplate.opsForHash().delete(sessionId, JwtInfo.JWT_REDIS_KEY);
        redisTemplate.opsForHash().put(sessionId, JwtInfo.JWT_REDIS_KEY, jwtInfo);
        redisTemplate.expireAt(sessionId, localDateTimeToDateForRenewToken(expire));
    }

    private static Date localDateTimeToDateForRenewToken(LocalDateTime expiredTime) {
        Instant instant = expiredTime
                .plus(6, DAYS)
                .plus(30, MINUTES)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        return Date.from(instant);
    }

    private List<String> parseJwt(String jwt) throws JsonProcessingException {
        String[] jwtSection = jwt.split("\\.");
        // JWT 는 header, payload, signature 가 "." 으로 연결되어있다. (header.payload.signature)
        String jwtPayload = jwtSection[1];

        byte[] decode = Base64.getDecoder().decode(jwtPayload);
        Payload payload = new ObjectMapper().readValue(new String(decode, StandardCharsets.UTF_8), Payload.class);

        return payload.getAuthorities();
    }

    public boolean isMemberInfoEmpty() {
        return this.name == null || this.email == null || this.phoneNumber == null;
    }

}