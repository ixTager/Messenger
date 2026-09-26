package com.anonchat.anonymousmessenger.unit;

import com.anonchat.anonymousmessenger.model.Dialog;
import com.anonchat.anonymousmessenger.model.Message;
import com.anonchat.anonymousmessenger.repository.DialogRepository;
import com.anonchat.anonymousmessenger.repository.MessageRepository;
import com.anonchat.anonymousmessenger.request.MessageRequest;
import com.anonchat.anonymousmessenger.utils.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class MessageUtilTest {
    @Mock
    private DialogRepository dialogRepository;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageUtil messageUtil;

    @Test
    public void ToEntity_ByMessageRequest_ReturnMessage() {
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
    public void ToEntity_ByMessageDTO_ReturnMessage() {}
}
