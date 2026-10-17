package com.example.fleetcontrol.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;


@Entity 
@Table(name = "users")
@Setter 
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false , unique=true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length= 20)
    private Role role;

    @Column(name="created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist 
    void onCreat(){
        createdAt = Instant.now();
    };
}
