package com.anonchat.anonymousmessenger.service.chat;

import com.anonchat.anonymousmessenger.dto.DialogDTO;
import com.anonchat.anonymousmessenger.repository.DialogRepository;
import com.anonchat.anonymousmessenger.utils.DialogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DialogNotificationService {
    private final DialogRepository dialogRepository;
    private final DialogUtil dialogUtil;
    private final DialogWebSocketService chatWebSocketService;

    @Transactional(readOnly = true)
    public void notifyUserDialogs(String uniqueUserId) {
        List<DialogDTO> dialogs = dialogRepository
                .findDistinctByUsers_UniqueUserId(uniqueUserId)
                .stream()
                .map(dialog -> dialogUtil.toDialogDTOByUniqueUserIdAndDialog(uniqueUserId, dialog))
                .filter(Objects::nonNull)
                .toList();

        chatWebSocketService.sendChats(
                uniqueUserId,
                dialogs
        );
    }
}
