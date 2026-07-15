package com.anonchat.anonymousmessenger.config;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, MessageDTO> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, MessageDTO> tpl = new RedisTemplate<>();
        tpl.setConnectionFactory(redisConnectionFactory);
        tpl.setDefaultSerializer(StringRedisSerializer.UTF_8);

        tpl.setKeySerializer(new StringRedisSerializer());
        tpl.setValueSerializer(new JacksonJsonRedisSerializer<>(MessageDTO.class));

        tpl.setHashKeySerializer(new StringRedisSerializer());
        tpl.setHashValueSerializer(new JacksonJsonRedisSerializer<>(MessageDTO.class));

        tpl.afterPropertiesSet();

        return tpl;
    }
}
