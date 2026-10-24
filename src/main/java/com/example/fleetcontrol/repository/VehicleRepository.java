package com.example.fleetcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import com.example.fleetcontrol.model.Vehicle;
import com.example.fleetcontrol.model.VehicleStatus;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {


    boolean existsByPlate(String plate);

    Optional<Vehicle> findByIdAndOwnerId(Long id, Long ownerId);

    Page<Vehicle> findByOwnerId(Long id, Pageable pageable);

    Page<Vehicle> findByOwnerIdAndStatus(Long id, VehicleStatus vehicleStatus, Pageable pageable);
}
