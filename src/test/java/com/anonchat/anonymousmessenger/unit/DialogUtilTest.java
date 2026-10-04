package com.anonchat.anonymousmessenger.unit;

import com.anonchat.anonymousmessenger.dto.DialogDTO;
import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.enumerating.MessageStatus;
import com.anonchat.anonymousmessenger.model.Dialog;
import com.anonchat.anonymousmessenger.model.Message;
import com.anonchat.anonymousmessenger.service.message.MessageService;
import com.anonchat.anonymousmessenger.utils.DialogUtil;
import com.anonchat.anonymousmessenger.utils.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class DialogUtilTest {

    @Mock
    private MessageUtil messageUtil;

    @InjectMocks
    private DialogUtil dialogUtil;

    @Test
    public void toDialogDTOByUniqueUserIdAndDialog_ByDialog_ReturnDialogDTO() {
        // Arrange
        String uniqueDialogId = "uniqueDialogId";
        String uniqueUserId = "uniqueUserId";
        String senderId = "anotherUserId";

        Message messageEntity = Message.builder()
                .content("content")
                .build();

        Dialog dialog = Dialog.builder()
                .uniqueDialogId(uniqueDialogId)
                .messages(Collections.singletonList(messageEntity))
                .build();

        MessageDTO messageDTO = MessageDTO.builder()
                .messageContent("content")
                .messageLocalSentAt(LocalDateTime.of(2026, 1, 1, 12, 0, 0))
                .messageStatus(MessageStatus.SENT)
                .uniqueUserId(senderId)
                .senderFirstName("FirstName")
                .senderLastName("LastName")
                .uniqueDialogId(uniqueDialogId)
                .build();

        Mockito.when(messageUtil.toMessageDTOByEntity(messageEntity)).thenReturn(messageDTO);

        DialogDTO dialogDTO = dialogUtil.toDialogDTOFromDialog(uniqueUserId, dialog);

        assertNotNull(dialogDTO);
        assertEquals(dialog.getUniqueDialogId(), dialogDTO.getUniqueDialogId());
        assertEquals("content", dialogDTO.getLastMessageContent());
        assertEquals("12:00:00", dialogDTO.getSentAtLastMessage());
        assertEquals("FirstName", dialogDTO.getFirstNameMember());
        assertEquals("LastName", dialogDTO.getLastNameMember());
        assertEquals(1, dialogDTO.getCountUnreadMessages());
    }
}
