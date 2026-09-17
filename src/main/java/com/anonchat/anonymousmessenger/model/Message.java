package com.anonchat.anonymousmessenger.model;

import com.anonchat.anonymousmessenger.enumerating.MessageStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "messages")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid_message")
    private String uuidMessage;

    @Column(name = "content")
    private String content;

    @Column(name = "instant_sent_at")
    private Instant instantSentAt;

    @Column(name = "local_sent_at")
    private LocalDateTime localSentAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MessageStatus status;

    @ManyToOne
    @JoinColumn(name = "dialog_id")
    private Dialog dialog;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
