package com.anonchat.anonymousmessenger.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

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

    @Column(name = "content")
    private String content;

    @Column(name = "sentAt")
    private Instant sentAt;

    @ManyToOne
    @JoinColumn(name = "dialog_id")
    private Dialog dialog;
}
