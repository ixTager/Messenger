package com.anonchat.anonymousmessenger.unit;

import com.anonchat.anonymousmessenger.dto.DialogDTO;
import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.enumerating.MessageStatus;
import com.anonchat.anonymousmessenger.model.Dialog;
import com.anonchat.anonymousmessenger.service.message.MessageService;
import com.anonchat.anonymousmessenger.utils.DialogUtil;
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
    private MessageService messageService;

    @InjectMocks
    private DialogUtil dialogUtil;

    @Test
    public void FromEntity_ByDialog_ReturnDialogDTO() {
        // Arrange
        String uniqueDialogId = "uniqueDialogId";

        MessageDTO messageDTO = MessageDTO.builder()
                .messageContent("content")
                .messageLocalSentAt(LocalDateTime.of(2026, 1, 1, 12, 0, 0))
                .messageStatus(MessageStatus.SENT)
                .senderFirstName("FirstName")
                .senderLastName("LastName")
                .uniqueDialogId(uniqueDialogId).build();

        List<MessageDTO> messageDTOList = Collections.singletonList(messageDTO);

        Dialog dialog = Dialog.builder()
                .uniqueDialogId(uniqueDialogId)
                .build();

        Mockito.when(messageService.getMessagesByDialogId(uniqueDialogId)).thenReturn(messageDTOList);

        DialogDTO dialogDTO = dialogUtil.fromEntity(dialog);

        assertNotNull(dialogDTO);
        assertEquals(dialog.getUniqueDialogId(), dialogDTO.getUniqueDialogId());
        assertEquals("content", dialogDTO.getLastMessageContent());
        assertEquals("12:00:00", dialogDTO.getSentAtLastMessage());
        assertEquals("FirstName", dialogDTO.getFirstNameMember());
    }
}
