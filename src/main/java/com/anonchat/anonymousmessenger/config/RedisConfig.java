package com.anonchat.anonymousmessenger.config;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, MessageDTO> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, MessageDTO> tpl = new RedisTemplate<>();
        tpl.setConnectionFactory(cf);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        JacksonJsonRedisSerializer<MessageDTO> jacksonJsonRedisSerializer =
                new JacksonJsonRedisSerializer<>(MessageDTO.class);

        tpl.setKeySerializer(new StringRedisSerializer());
        tpl.setValueSerializer(jacksonJsonRedisSerializer);

        tpl.setHashKeySerializer(new StringRedisSerializer());
        tpl.setHashValueSerializer(jacksonJsonRedisSerializer);

        tpl.afterPropertiesSet();
        return tpl;
    }
}
