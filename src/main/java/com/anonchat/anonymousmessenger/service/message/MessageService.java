package com.anonchat.anonymousmessenger.service.message;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.dto.MessageStatusDTO;
import com.anonchat.anonymousmessenger.enumerating.MessageStatus;
import com.anonchat.anonymousmessenger.exceptions.UserNotFoundException;
import com.anonchat.anonymousmessenger.request.MessageRequest;
import com.anonchat.anonymousmessenger.model.Message;
import com.anonchat.anonymousmessenger.model.User;
import com.anonchat.anonymousmessenger.rabbitmq.MessageProducer;
import com.anonchat.anonymousmessenger.repository.MessageRepository;
import com.anonchat.anonymousmessenger.service.UserService;
import com.anonchat.anonymousmessenger.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageProducer messageProducer;
    private final MessageUtil messageUtil;
    private final MessageRepository messageRepository;
    private final UserService userService;
//    private final CacheMessageService cacheMessageService;

    @Value("${database.count.last-messages}")
    private int countLastMessages;

    @Transactional
    public void saveMessage(MessageDTO message) {
        Message msg = messageUtil.toEntity(message);
        messageRepository.save(msg);
    }

    public boolean send(MessageRequest messageRequest) {
        try {
            User currentUser = userService.getCurrentUser();
            Message message = messageUtil.toEntity(messageRequest);

            Instant now = Instant.now();
            message.setUuidMessage(UUID.randomUUID().toString());
            message.setUser(currentUser);
            message.setInstantSentAt(now);
            message.setLocalSentAt(LocalDateTime.ofInstant(now, ZoneId.systemDefault()));
            message.setStatus(MessageStatus.SENT);

            MessageDTO messageDTO = messageUtil.fromEntity(message);
            messageProducer.sendMessage(messageDTO);
        }
        catch (UserNotFoundException e) {
            System.err.println("User not found");
            return false;
        }
        return true;
    }

    @Transactional
    public void markMessagesAsRead(String uniqueDialogId) {
        User currentUser = userService.getCurrentUser();

        List<Message> unreadMessages =
                messageRepository.findUnreadMessagesByDialogIdAndNotUser(
                        uniqueDialogId, currentUser.getUniqueUserId());

        if (unreadMessages.isEmpty()) return;

        unreadMessages.forEach(m -> m.setStatus(MessageStatus.READ));
        messageRepository.saveAll(unreadMessages);

        unreadMessages.forEach(m -> {
            MessageStatusDTO dto = MessageStatusDTO.builder()
                    .uuidMessage(m.getUuidMessage())
                    .uniqueDialogId(uniqueDialogId)
                    .status(MessageStatus.READ)
                    .uniqueUserId(currentUser.getUniqueUserId())
                    .build();
            messageProducer.sendStatusUpdate(dto);
        });
    }

    @Transactional(readOnly = true)
    public List<MessageDTO> getMessagesByDialogId(String uniqueDialogId) {
        Pageable pageable = PageRequest.of(0, countLastMessages, Sort.by("instantSentAt").descending());

        List<MessageDTO> dtosFromDb = messageRepository.findByDialog_UniqueDialogId(uniqueDialogId, pageable)
                .stream()
                .map(messageUtil::fromEntity)
                .toList();

        List<MessageDTO> orderedDtos = new ArrayList<>(dtosFromDb);
        Collections.reverse(orderedDtos);

        //TODO
//        if (!dtosFromDb.isEmpty()) cacheMessageService.cacheMessageDTOList(uniqueDialogId, orderedDtos);

        return orderedDtos;

    }
}
