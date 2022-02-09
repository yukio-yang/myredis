package com.yukio.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author yukio
 * @create 2020-06-29 16:43
 */
@Configuration
public class CacheConfig {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired//RedisConfig那面已经注入了，这里直接使用
    private StringRedisSerializer stringRedisSerializer;

    @Autowired
    private Jackson2JsonRedisSerializer jackson2JsonRedisSerializer;

    /*Redis和SoringCache整合的缓存管理器*/
    @Bean(name = "redisCacheManager")
    public RedisCacheManager redisCacheManager() {
        //创建redis缓存默认配置
        RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig();
        //配置序列化规则
        conf = conf.serializeKeysWith(RedisSerializationContext.SerializationPair
                .fromSerializer(stringRedisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(jackson2JsonRedisSerializer))
                //设置缓存的过期时间
                .entryTtl(Duration.ofMinutes(-1));//设置缓存的默认过期时间
        RedisCacheManager redisCacheManager = RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(conf)
                .build();
        return redisCacheManager;
    }

}
