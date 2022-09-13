package com.nhnacademy.marketgg.client.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 캐시의 설정을 위한 Configuration 클래스입니다.
 *
 * @author 조현진
 */
@EnableCaching
@Configuration
public class CacheConfig {

    /**
     * Resource 경로 내에 있는 ehcache-config.xml을 설정 정보로 등록합니다.
     *
     * @return EhCacheManagerFactoryBean을 반환합니다.
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache-config.xml"));
        ehCacheManagerFactoryBean.setShared(true);
        return ehCacheManagerFactoryBean;
    }

    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean) {
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
        ehCacheCacheManager.setCacheManager(ehCacheManagerFactoryBean.getObject());
        return ehCacheCacheManager;
    }

    /**
     * 레디스 캐시 매니저를 설정합니다.
     * 직렬화는 Jackson2를 사용하고, 300초의 수명을 갖도록 설정했습니다.
     *
     * @param connectionFactory - RedisConnectionFactory를 주입받습니다.
     * @return - redisCacheManager를 반환합니다.
     */
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                                                                                 .serializeKeysWith(
                                                                                     RedisSerializationContext.SerializationPair.fromSerializer(
                                                                                         new StringRedisSerializer()))
                                                                                 .serializeValuesWith(
                                                                                     RedisSerializationContext.SerializationPair.fromSerializer(
                                                                                         new GenericJackson2JsonRedisSerializer()))
                                                                                 .disableCachingNullValues()
                                                                                 .entryTtl(Duration.ofSeconds(300));

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
                                                         .cacheDefaults(redisCacheConfiguration).build();
    }

    /**
     * ehCache와 redis 캐시를 동시에 사용하기 위한 매니저입니다.
     *
     * @param ehCacheCacheManager - ehCacheManager를 주입받아 List로 등록합니다.
     * @param redisCacheManager   - redisCacheManager를 주입받아 List로 등록합니다.
     * @return - eh 캐시와 redis 캐시 매니저가 등록된 compositeCacheManager를 반환합니다.
     */
    @Bean
    @Primary
    public CacheManager compositeCacheManager(EhCacheCacheManager ehCacheCacheManager, CacheManager redisCacheManager) {
        List<CacheManager> managers = new ArrayList<>();
        managers.add(ehCacheCacheManager);
        managers.add(redisCacheManager);
        CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
        compositeCacheManager.setCacheManagers(managers);
        return compositeCacheManager;
    }
}
