package com.anonchat.anonymousmessenger.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "dialogs")
public class Dialog {
    @Id
    String id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "dialogs_members",
            joinColumns = @JoinColumn(name = "dialog_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    Set<User> members;

    @Column(name = "createdAt")
    Instant createdAt;

    @OneToMany(mappedBy = "dialog")
    List<Message> messages;

}
