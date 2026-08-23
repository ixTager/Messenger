package com.anonchat.anonymousmessenger.repository;

import com.anonchat.anonymousmessenger.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByDialog_UniqueDialogId(String dialogUniqueDialogId, Pageable pageable);
}

