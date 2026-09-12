package com.anonchat.anonymousmessenger.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "persons")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "profile")
    private User user;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
}
