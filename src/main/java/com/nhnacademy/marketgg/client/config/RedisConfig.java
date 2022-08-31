package com.nhnacademy.marketgg.client.config;

import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * Redis 설정을 담당합니다.
 *
 * @author 윤동열
 * @version 1.0.0
 */
@Configuration
public class RedisConfig {

    private final String host;
    private final int port;
    private final int database;
    private final String password;

    public RedisConfig(@Value("${gg.redis.host}") String host,
                       @Value("${gg.redis.port}") int port,
                       @Value("${gg.redis.database}") int database,
                       @Value("${gg.redis.password}") String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.password = password;
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

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("_SESSION");
        serializer.setCookieMaxAge(1800);
        serializer.setCookiePath("/");
        serializer.setUseHttpOnlyCookie(true);

        return serializer;
    }

    /**
     * RedisTemplate 을 스프링 빈 등록합니다.
     *
     * @param redisConnectionFactory - 스프링 빈으로 등록된 RedisConnectionFactory
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(JwtInfo.class));

        return redisTemplate;
    }

}
