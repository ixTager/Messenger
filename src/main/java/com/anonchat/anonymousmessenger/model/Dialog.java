package com.anonchat.anonymousmessenger.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dialogs")
public class Dialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unique_dialog_id", unique = true, nullable = false)
    private String uniqueDialogId;

    @Builder.Default
    @ManyToMany(mappedBy = "dialogs")
    private Set<User> users = new HashSet<>();

    @Column(name = "dialog_key", unique = true)
    private String dialogKey;

    @OneToMany(mappedBy = "dialog")
    @Builder.Default
    private List<Message> messages = new ArrayList<>();
}
