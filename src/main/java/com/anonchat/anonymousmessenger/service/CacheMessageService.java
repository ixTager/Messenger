package com.anonchat.anonymousmessenger.service;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class CacheMessageService {
    @Value("${database.count.last-messages}")
    private int lastMessagesCount;

    private final RedisTemplate<String, MessageDTO> redisTemplate;

    private final static String CACHE_KAY_PREFIX = "dialog:%s:messages";

    public void cacheMessageDTO(String uniqueDialogId, MessageDTO messageDTO) {
        String cacheKey = String.format(CACHE_KAY_PREFIX, uniqueDialogId);
        redisTemplate.opsForList().rightPush(cacheKey, messageDTO);
        redisTemplate.opsForList().trim(cacheKey, -lastMessagesCount, -1);
        log.info("Cache message for uniqueDialogId={} has been cached", uniqueDialogId);
    }

    public void cacheMessageDTOList(String uniqueDialogId, List<MessageDTO> dtos) {
        String cacheKey = String.format(CACHE_KAY_PREFIX, uniqueDialogId);
        redisTemplate.delete(cacheKey);
        redisTemplate.opsForList().rightPushAll(cacheKey, dtos);
        redisTemplate.opsForList().trim(cacheKey, -lastMessagesCount, -1);
        log.info("Cache messageList for uniqueDialogId={} has been cached", uniqueDialogId);
    }

    public List<MessageDTO> getMessagesByUniqueDialogId(String uniqueDialogId) {
        String cacheKey = String.format(CACHE_KAY_PREFIX, uniqueDialogId);
        List<MessageDTO> messages = redisTemplate.opsForList().range(cacheKey, 0, -1);
        log.info("Get messages from cache was done for uniqueDialogId={} ", uniqueDialogId);
        return messages != null ? messages : Collections.emptyList();

    }

}
