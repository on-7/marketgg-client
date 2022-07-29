package com.nhnacademy.marketgg.client.config;

import com.nhnacademy.marketgg.client.exception.SecureManagerException;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Redis 설정을 담당합니다.
 *
 * @version 1.0.0
 */
@Configuration
public class RedisConfig {

    private final RestTemplate restTemplate;
    private final String host;
    private final int port;
    private final int database;
    private final String password;

    public RedisConfig(@Qualifier("clientCertificateAuthenticationRestTemplate") RestTemplate restTemplate,
                       @Value("${gg.redis.password-url}") String redisPasswordUrl,
                       @Value("${gg.redis.url}") String redisInfoUrl) {
        this.restTemplate = restTemplate;
        String[] info = this.getRedisInfo(redisInfoUrl);
        this.host = info[0];
        this.port = Integer.parseInt(info[1]);
        this.database = Integer.parseInt(info[2]);
        this.password = this.getRedisPassword(redisPasswordUrl);
    }

    /**
     * Redis 연결과 관련된 설정을 하는 RedisConnectionFactory 를 스프링 빈으로 등록한다.
     *
     * @return RedisConnectionFactory
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();

        configuration.setHostName(host);
        configuration.setPort(port);
        configuration.setPassword(password);
        configuration.setDatabase(database);

        return new LettuceConnectionFactory(configuration);
    }

    /**
     * RedisTemplate 을 스프링 빈 등록합니다.
     *
     * @param redisConnectionFactory - 스프링 빈으로 등록된 RedisConnectionFactory
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, JwtInfo> redisTemplate(
        RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, JwtInfo> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(JwtInfo.class));

        return redisTemplate;
    }

    private String[] getRedisInfo(String infoUrl) {
        // Map<String, Map<String, String>> response =
        //     restTemplate.getForObject(infoUrl, Map.class);

        ResponseEntity<Map<String, Map<String, String>>> response =
            restTemplate.exchange(infoUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            });

        String connectInfo = Optional.ofNullable(response.getBody())
                                     .orElseThrow(SecureManagerException::new)
                                     .get("body")
                                     .get("secret");

        String[] info = connectInfo.split(":");

        if (info.length != 3) {
            throw new SecureManagerException();
        }

        return info;
    }

    private String getRedisPassword(String passwordUrl) {
        // Map<String, Map<String, String>> response =
        //     restTemplate.getForObject(passwordUrl, Map.class);

        ResponseEntity<Map<String, Map<String, String>>> response =
            restTemplate.exchange(passwordUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            });

        return Optional.ofNullable(response.getBody())
                       .orElseThrow(SecureManagerException::new)
                       .get("body")
                       .get("secret");
    }

}
