package com.anonchat.anonymousmessenger.unit;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.enumerating.MessageStatus;
import com.anonchat.anonymousmessenger.model.Dialog;
import com.anonchat.anonymousmessenger.model.Message;
import com.anonchat.anonymousmessenger.model.User;
import com.anonchat.anonymousmessenger.repository.DialogRepository;
import com.anonchat.anonymousmessenger.repository.UserRepository;
import com.anonchat.anonymousmessenger.request.MessageRequest;
import com.anonchat.anonymousmessenger.utils.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class MessageUtilTest {
    @Mock
    private DialogRepository dialogRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MessageUtil messageUtil;

    @Test
    public void ToEntity_ByMessageRequest_ReturnMessage() {
        // Arrange
        String uniqueDialogId = "dialog-1234";

        MessageRequest messageRequest = MessageRequest.builder()
                .uniqueDialogId(uniqueDialogId)
                .content("Content")
                .build();

        Dialog mockDialog = Dialog.builder()
                .uniqueDialogId(uniqueDialogId)
                .build();

        Mockito.when(dialogRepository.findDialogByUniqueDialogId(uniqueDialogId))
                .thenReturn(Optional.of(mockDialog));

        Message result = messageUtil.toEntity(messageRequest);

        assertNotNull(result);
        assertEquals("Content", result.getContent());
        assertEquals(mockDialog, result.getDialog());
    }

    @Test
    public void toEntity_ValidMessageDTO_ReturnsFullyMappedMessage() {
        // Arrange
        LocalDateTime localDateTime = LocalDateTime.now();
        String uniqueDialogId = "dialog-1234";
        String uniqueUserId = "user-12345";

        MessageDTO messageDTO = MessageDTO.builder()
                .id(10L)
                .messageUUID("message-uuid")
                .messageContent("message-content")
                .messageStatus(MessageStatus.SENT)
                .uniqueDialogId(uniqueDialogId)
                .uniqueUserId(uniqueUserId)
                .messageLocalSentAt(localDateTime)
                .build();

        User mockUser = User.builder()
                .id(1L)
                .uniqueUserId(uniqueUserId)
                .build();

        Dialog mockDialog = Dialog.builder()
                .id(2L)
                .uniqueDialogId(uniqueDialogId)
                .build();

        Mockito.when(dialogRepository.findDialogByUniqueDialogId(uniqueDialogId))
                .thenReturn(Optional.of(mockDialog));

        Mockito.when(userRepository.findByUniqueUserIdIgnoreCase(uniqueUserId))
                .thenReturn(Optional.of(mockUser));

        Message result = messageUtil.toEntity(messageDTO);

        assertNotNull(result);
        assertEquals(messageDTO.getId(), result.getId());
        assertEquals("message-content", result.getContent());
        assertEquals("message-uuid", result.getUuidMessage());
        assertEquals(MessageStatus.SENT, result.getStatus());
        assertEquals(localDateTime, result.getLocalSentAt());
        assertEquals(localDateTime.atZone(ZoneId.systemDefault()).toInstant(), result.getInstantSentAt());

        assertEquals(mockUser, result.getUser());
        assertEquals(mockDialog, result.getDialog());
    }
}
