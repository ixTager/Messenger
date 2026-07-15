package com.anonchat.anonymousmessenger.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "dialogs")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unique_dialog_id", unique = true, nullable = false)
    private String uniqueDialogId;

    @ManyToMany(mappedBy = "dialogs")
    @Builder.Default
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "dialog")
    @Builder.Default
    private List<Message> messages = new ArrayList<>();
}
