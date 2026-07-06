package com.anonchat.anonymousmessenger.config;

import com.anonchat.anonymousmessenger.entity.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Message> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, Message> tpl = new RedisTemplate<>();
        tpl.setConnectionFactory(cf);

        ObjectMapper mapper = new ObjectMapper();

        JacksonJsonRedisSerializer<Message> jacksonJsonRedisSerializer =
                new JacksonJsonRedisSerializer<>(mapper, Message.class);

        tpl.setKeySerializer(new StringRedisSerializer());
        tpl.setValueSerializer(jacksonJsonRedisSerializer);

        tpl.setHashKeySerializer(new StringRedisSerializer());
        tpl.setHashValueSerializer(jacksonJsonRedisSerializer);

        tpl.afterPropertiesSet();
        return tpl;
    }
}
