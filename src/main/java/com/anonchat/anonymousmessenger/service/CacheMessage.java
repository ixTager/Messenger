package com.anonchat.anonymousmessenger.service;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheMessage {
    private final RedisTemplate<String, MessageDTO> redisTemplate;

    private final static String CACHE_KAY_PREFIX = "dialog:%s:messages";

    public void cacheMessage(Long dialogId, MessageDTO messageDTO) {
        String cacheKey = String.format(CACHE_KAY_PREFIX, dialogId.toString());
        redisTemplate.opsForList().rightPush(cacheKey, messageDTO);
        redisTemplate.opsForList().trim(cacheKey, -20, -1);
    }

    public List<MessageDTO> getMessages(Long dialogId) {
        String cacheKey = String.format(CACHE_KAY_PREFIX, dialogId.toString());
        List<MessageDTO> messages = redisTemplate.opsForList().range(cacheKey, 0, -1);
        return messages != null ? messages : Collections.emptyList();
    }
}
