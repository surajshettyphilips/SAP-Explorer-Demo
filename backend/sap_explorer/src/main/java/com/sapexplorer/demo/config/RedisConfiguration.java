package com.sapexplorer.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.sapexplorer.demo.models.Table;



@Configuration
public class RedisConfiguration {
	@Bean
	ReactiveRedisOperations<String, Table> redisOperations(ReactiveRedisConnectionFactory factory) {
		Jackson2JsonRedisSerializer<Table> serializer = new Jackson2JsonRedisSerializer<>(Table.class);

		RedisSerializationContext.RedisSerializationContextBuilder<String, Table> builder =
				RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

		RedisSerializationContext<String, Table> context = builder.value(serializer).build();

		return new ReactiveRedisTemplate<>(factory, context);
	}
	


}