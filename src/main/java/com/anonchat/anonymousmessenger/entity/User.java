package com.anonchat.anonymousmessenger.entity;

import com.anonchat.anonymousmessenger.enumeration.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Table(name = "users")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "password", nullable = false, length = 255)
    String password;

    @Column(name = "unique_id", nullable = false, unique = true)
    String userId;

    @Column(name = "role", length = 10)
    @Enumerated(EnumType.STRING)
    UserRole role;

    @ManyToMany(mappedBy = "members")
    private Set<Dialog> dialogs;

}
