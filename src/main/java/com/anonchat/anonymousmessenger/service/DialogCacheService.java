package com.anonchat.anonymousmessenger.service;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DialogCacheService {
    private final RedisTemplate<String, MessageDTO> redis;

    private static final String CACHE_KEY_PREFIX = "dialog:%s:messages";

    public void cacheMessages(String dialogId, MessageDTO message) {
        String cacheKey = CACHE_KEY_PREFIX + dialogId;
        redis.opsForList().rightPush(cacheKey, message);
        redis.opsForList().trim(cacheKey, -20, -1); // Берем 20 элементов

    }

    public List<MessageDTO> getLatestMessages(String dialogId) {
        String cacheKey = CACHE_KEY_PREFIX + dialogId;
        List<MessageDTO> messages = redis.opsForList().range(cacheKey, 0, -1);

        return messages != null ? messages : Collections.emptyList();
    }
}
