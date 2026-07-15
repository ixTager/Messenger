package com.anonchat.anonymousmessenger.service;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.entity.Message;
import com.anonchat.anonymousmessenger.rabbitmq.MessageProducer;
import com.anonchat.anonymousmessenger.repository.MessageRepository;
import com.anonchat.anonymousmessenger.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageProducer messageProducer;
    private final CacheMessage cacheMessage;
    private final MessageUtil messageUtil;
    private final MessageRepository messageRepository;

    @Transactional
    public void saveMessage(MessageDTO message) {
        Message msg = messageUtil.toEntity(message);
        messageRepository.save(msg);
    }

    public void send(Message message){
        MessageDTO messageDTO = messageUtil.fromEntity(message);
        cacheMessage.cacheMessage(messageDTO.getDialogId(), messageDTO);
        messageProducer.sendMessage(messageDTO);
    }
}
