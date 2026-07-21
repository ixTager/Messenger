package com.anonchat.anonymousmessenger.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unique_user_id", unique = true, nullable = false)
    private String uniqueUserId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private UserProfile profile;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_dialogs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "dialog_id")
    )
    @Builder.Default
    private Set<Dialog> dialogs = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Message> messages = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 10)
    private UserRole role;
}
