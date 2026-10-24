package com.example.fleetcontrol.repository;

import com.example.fleetcontrol.model.Maintenance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    List<Maintenance> findByVehicleIdOrderByServiceDateDesc(Long vehicleId);

    Optional<Maintenance> findByIdAndVehicleOwnerId(Long id,Long ownerId);


}
