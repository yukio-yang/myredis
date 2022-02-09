package com.yukio.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author yukio
 * @create 2022-01-25 11:05
 * 这个是luccture客户端的
 */
@Configuration
public class RedisConfig {

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Bean
	public StringRedisSerializer stringRedisSerializer() {
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		return  stringRedisSerializer;
	}


	//spring-web依赖存在jackson依赖，下面序列化需要使用
	@Bean
	public Jackson2JsonRedisSerializer jackson2JsonRedisSerializer () {
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer
				= new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL,
				JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		//解决jackson2无法序列化LocalDateTime的问题
		objectMapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
		objectMapper.registerModule(new JavaTimeModule());

		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

		return jackson2JsonRedisSerializer;
	}

	@Bean
	public StringRedisTemplate stringRedisTemplate() {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
		//开启事物支持
		stringRedisTemplate.setEnableTransactionSupport(true);
		return stringRedisTemplate;
	}

	@Bean
	public RedisTemplate redisTemplate() {//这个模板是单单例，我们配置了系统就不会配置了
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		//默认使用jdk的序列化，导致存进去看着像乱码
		redisTemplate.setKeySerializer(stringRedisSerializer());
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
		redisTemplate.setHashKeySerializer(stringRedisSerializer());
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());
		// 开启事物支持
		redisTemplate.setEnableTransactionSupport(true);
		return redisTemplate;
	}
}

