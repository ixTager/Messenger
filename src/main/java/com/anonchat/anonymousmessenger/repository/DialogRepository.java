package com.anonchat.anonymousmessenger.repository;


import com.anonchat.anonymousmessenger.entity.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, String> {
    Optional<Dialog> findDialogByUniqueId(String dialogId);
    List<Dialog> findByMembersUserId(String userId);
}
