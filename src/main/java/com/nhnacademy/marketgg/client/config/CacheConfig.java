package com.nhnacademy.marketgg.client.config;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;
import java.util.List;

@EnableCaching
@Configuration
public class CacheConfig {

//    @Bean
//    public CacheManager cacheManager() {
//        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
//        simpleCacheManager.setCaches(List.of(new ConcurrentMapCache("category")));
//        return simpleCacheManager;
//    }
//


    @Bean
    public CacheManager ehCacheManager() {
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();

        CacheConfigurationBuilder<SimpleKey, List> configuration =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                SimpleKey.class,
                                List.class,
                                ResourcePoolsBuilder
                                        .newResourcePoolsBuilder().offheap(1, MemoryUnit.MB))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(300)));

        javax.cache.configuration.Configuration<SimpleKey, List> categoryConfiguration =
                Eh107Configuration.fromEhcacheCacheConfiguration(configuration);

        cacheManager.createCache("category", categoryConfiguration);
        return cacheManager;

    }

    @Bean
    public org.springframework.cache.CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(300));
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory).cacheDefaults(redisCacheConfiguration).build();
    }
}
