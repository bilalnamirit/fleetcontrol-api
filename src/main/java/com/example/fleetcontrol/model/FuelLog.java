package com.example.fleetcontrol.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.math.BigDecimal;


@Entity
@Table(name = "fuel_logs")
@Getter
@Setter
@NoArgsConstructor
public class FuelLog {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "log_date", nullable= false)
    private LocalDate logDate;

    @PositiveOrZero
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal liters;

    @PositiveOrZero
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @PositiveOrZero
    @Column(nullable = false)
    private Integer mileage;

    @ManyToOne( fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

}
