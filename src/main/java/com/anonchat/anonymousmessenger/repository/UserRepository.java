package com.anonchat.anonymousmessenger.repository;

import com.anonchat.anonymousmessenger.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailIgnoreCase(String email);
    boolean existsById(Long id);
    Optional<User> findByUserId(String userId);
}
