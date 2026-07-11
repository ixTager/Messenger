package com.anonchat.anonymousmessenger.service;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class DialogCacheService {
    private final RedisTemplate<String, MessageDTO> redis;

    private static final String CACHE_KEY_PREFIX = "dialog:%s:messages";

    public void cacheMessages(String dialogId, MessageDTO message) {
        String cacheKey = String.format(CACHE_KEY_PREFIX, dialogId);
        redis.opsForList().rightPush(cacheKey, message);
        redis.opsForList().trim(cacheKey, -20, -1); // Берем 20 элементов
        redis.expire(cacheKey, 3600, TimeUnit.SECONDS);
    }

    public List<MessageDTO> getLatestMessages(String dialogId) {
        String cacheKey = String.format(CACHE_KEY_PREFIX, dialogId);
        List<MessageDTO> messages = redis.opsForList().range(cacheKey, 0, -1);

        return messages != null ? messages : Collections.emptyList();
    }
}
