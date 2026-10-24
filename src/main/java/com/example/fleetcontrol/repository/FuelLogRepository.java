package com.example.fleetcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.fleetcontrol.model.FuelLog;

import java.math.BigDecimal;
import java.util.List;

public interface FuelLogRepository extends JpaRepository<FuelLog, Long> {

    List<FuelLog> findByVehicleIdOrderByLogDateDesc(Long vehicleId);

    @Query("""
            select sum(f.liters) as totalLiters,
                    sum(f.totalPrice) as totalCost,
                    count(f) as logCount
            from FuelLog f
            where f.vehicle.id = :vehicleId        
        """)
    FuelStats getStats(@Param("vehicleId") Long vehicleId);

    interface FuelStats{
        BigDecimal getTotalLiters();

        BigDecimal getTotalCost();

        long getLogCount();
    }

}
