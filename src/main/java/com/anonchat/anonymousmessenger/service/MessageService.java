package com.anonchat.anonymousmessenger.service;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.dto.MessageRequest;
import com.anonchat.anonymousmessenger.entity.Message;
import com.anonchat.anonymousmessenger.entity.User;
import com.anonchat.anonymousmessenger.rabbitmq.MessageProducer;
import com.anonchat.anonymousmessenger.repository.MessageRepository;
import com.anonchat.anonymousmessenger.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageProducer messageProducer;
    private final CacheMessageService cacheMessageService;
    private final MessageUtil messageUtil;
    private final MessageRepository messageRepository;
    private final UserService userService;

    @Value("${database.count.last-messages}")
    private int countLastMessages;

    @Transactional
    public void saveMessage(MessageDTO message) {
        Message msg = messageUtil.toEntity(message);
        messageRepository.save(msg);
    }

    public void send(MessageRequest messageRequest) {
        User user = userService.getCurrentUser();
        Message message = messageUtil.toEntity(messageRequest);
        message.setUser(user);
        message.setSentAt(Instant.now());

        MessageDTO messageDTO = messageUtil.fromEntity(message);
        cacheMessageService.cacheMessageDTO(messageDTO.getUniqueDialogId(), messageDTO);
        messageProducer.sendMessage(messageDTO);
    }

    public List<MessageDTO> getMessagesByDialogId(String uniqueDialogId) {

        Pageable pageable = PageRequest.of(
                0,
                countLastMessages,
                Sort.by("sentAt").descending()
        );

        List<Message> fromDb =
                messageRepository.findByDialog_UniqueDialogId(
                        uniqueDialogId,
                        pageable
                );

        return fromDb.stream()
                .map(messageUtil::fromEntity)
                .toList();
    }
}
