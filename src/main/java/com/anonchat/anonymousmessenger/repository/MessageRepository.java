package com.anonchat.anonymousmessenger.repository;

import com.anonchat.anonymousmessenger.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> getMessagesByDialogId(Long id);
}
