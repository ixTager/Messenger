package com.anonchat.anonymousmessenger.repository;

import com.anonchat.anonymousmessenger.entity.Dialog;
import com.anonchat.anonymousmessenger.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {
    Optional<Dialog> findDialogByUniqueDialogId(String id);
    List<Dialog> findDialogsBy(String uniqueUserId);
    Optional<Dialog> findDialogByDialogKey(String dialogKey);
}

