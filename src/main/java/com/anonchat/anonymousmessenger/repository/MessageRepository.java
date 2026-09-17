package com.anonchat.anonymousmessenger.repository;

import com.anonchat.anonymousmessenger.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    @Query("""
    SELECT m
    FROM Message m
    WHERE m.dialog.uniqueDialogId = :dialogId
      AND m.user.uniqueUserId <> :userId
      AND m.status = 'SENT'
""")
    List<Message> findUnreadMessagesByDialogIdAndNotUser(
            @Param("dialogId") String dialogId,
            @Param("userId") String userId
    );

    List<Message> findByDialog_UniqueDialogId(String dialogUniqueDialogId, Pageable pageable);
    Optional<Message> findMessageByUuidMessage(String uuid);
}

