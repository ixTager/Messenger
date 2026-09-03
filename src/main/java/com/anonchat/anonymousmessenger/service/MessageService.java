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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageProducer messageProducer;
    private final MessageUtil messageUtil;
    private final MessageRepository messageRepository;
    private final UserService userService;
    private final CacheMessageService cacheMessageService;

    @Value("${database.count.last-messages}")
    private int countLastMessages;

    @Transactional
    public void saveMessage(MessageDTO message) {
        Message msg = messageUtil.toEntity(message);
        messageRepository.save(msg);
    }

    public void send(MessageRequest messageRequest) {
        User currentUser = userService.getCurrentUser();
        Message message = messageUtil.toEntity(messageRequest);
        message.setUser(currentUser);
        message.setSentAt(Instant.now());

        MessageDTO messageDTO = messageUtil.fromEntity(message);
        messageProducer.sendMessage(messageDTO);
    }

    public List<MessageDTO> getMessagesByDialogId(String uniqueDialogId) {
        List<MessageDTO> cachedMessages = cacheMessageService.getMessagesByUniqueDialogId(uniqueDialogId);
        if (cachedMessages.size() == countLastMessages) return cachedMessages;

        Pageable pageable = PageRequest.of(0, countLastMessages, Sort.by("sentAt").descending());
        List<MessageDTO> dtosFromDb = messageRepository.findByDialog_UniqueDialogId(uniqueDialogId, pageable)
                .stream()
                .map(messageUtil::fromEntity)
                .toList();

        List<MessageDTO> orderedDtos = new ArrayList<>(dtosFromDb);
        Collections.reverse(orderedDtos);

        if (!dtosFromDb.isEmpty()) cacheMessageService.cacheMessageDTOList(uniqueDialogId, dtosFromDb);

        return orderedDtos;
    }
}
