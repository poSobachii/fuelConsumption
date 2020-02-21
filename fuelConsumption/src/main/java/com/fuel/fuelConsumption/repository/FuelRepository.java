package com.fuel.fuelConsumption.repository;

import com.fuel.fuelConsumption.entity.FuelConsumptionRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import com.fuel.fuelConsumption.rest.Utility;

public interface FuelRepository extends JpaRepository<FuelConsumptionRequestEntity, Long> {
    List<FuelConsumptionRequestEntity> findListByDriverId(String driverId);

    @Query("select f from FuelEntryxx f where f.consumptionDate between :startDate AND :endDate")
    List<FuelConsumptionRequestEntity> findListByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);
}

