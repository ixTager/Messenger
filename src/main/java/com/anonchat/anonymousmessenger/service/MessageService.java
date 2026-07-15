package com.anonchat.anonymousmessenger.service;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.entity.Message;
import com.anonchat.anonymousmessenger.rabbitmq.MessageProducer;
import com.anonchat.anonymousmessenger.repository.DialogRepository;
import com.anonchat.anonymousmessenger.repository.MessageRepository;
import com.anonchat.anonymousmessenger.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageProducer messageProducer;
    private final CacheMessageService cacheMessageService;
    private final MessageUtil messageUtil;
    private final MessageRepository messageRepository;
    private final DialogRepository dialogRepository;

    @Transactional
    public void saveMessage(MessageDTO message) {
        Message msg = messageUtil.toEntity(message);
        messageRepository.save(msg);
    }

    public void send(Message message){
        MessageDTO messageDTO = messageUtil.fromEntity(message);
        cacheMessageService.cacheMessage(messageDTO.getDialogId(), messageDTO);
        messageProducer.sendMessage(messageDTO);
    }

    public List<MessageDTO> getMessagesByDialogId(Long id){
        List<MessageDTO> messages = cacheMessageService.getMessages(id);

        if (!messages.isEmpty()) {
            return messages;
        }

        List<Message> fromDb = messageRepository.getMessagesByDialogId(id);
        List<MessageDTO> dtos = fromDb.stream()
                .map(messageUtil::fromEntity)
                .toList();
        cacheMessageService.cacheMessages(id, dtos);
        return dtos;

    }
}
