package com.anonchat.anonymousmessenger.service;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheMessageService {
    @Value("${database.count.last-messages}")
    private int lastMessagesCount;

    private final RedisTemplate<String, MessageDTO> redisTemplate;

    private final static String CACHE_KAY_PREFIX = "dialog:%s:messages";

    public void cacheMessage(String uniqueDialogId, MessageDTO messageDTO) {
        String cacheKey = String.format(CACHE_KAY_PREFIX, uniqueDialogId);
        redisTemplate.opsForList().rightPush(cacheKey, messageDTO);
        redisTemplate.opsForList().trim(cacheKey, -lastMessagesCount, -1);
    }

    public void cacheMessageList(String uniqueDialogId, List<MessageDTO> dtos) {
        String cacheKey = String.format(CACHE_KAY_PREFIX, uniqueDialogId);
        redisTemplate.opsForList().rightPushAll(cacheKey, dtos);
        redisTemplate.opsForList().trim(cacheKey, -lastMessagesCount, -1);
    }

    public List<MessageDTO> getMessages(String uniqueDialogId) {
        String cacheKey = String.format(CACHE_KAY_PREFIX, uniqueDialogId);
        List<MessageDTO> messages = redisTemplate.opsForList().range(cacheKey, 0, -1);
        return messages != null ? messages : Collections.emptyList();
    }
}
