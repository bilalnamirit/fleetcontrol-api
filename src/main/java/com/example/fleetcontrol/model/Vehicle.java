package com.example.fleetcontrol.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table( name = "vehicles")
@Getter
@Setter 
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( unique = true, nullable = false, length = 15)
    private String plate;

    @Column(nullable = false, length = 50)
    private String model;

    @Column(nullable = false, length = 50)
    private String brand;

    @Column(nullable = false)
    private Integer year;

    @PositiveOrZero
    @Column(nullable = false)
    private Integer mileage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VehicleStatus status; 

    @ManyToOne( fetch = FetchType.LAZY, optional = false)
    @JoinColumn( name = "owner_id", nullable = false)
    private User owner;

    @Column( name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreat(){
        createdAt = Instant.now();
    }

}
