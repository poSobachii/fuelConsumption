package com.fuel.fuelConsumption.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity(name = "FuelEntry")
@Table(name = "fuelentry")
public class FuelConsumptionRequestEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private long id;

    @Column(name = "fuelType")
    private String fuelType;

    @Column(name = "pricePerLtr")
    private double pricePerLtr;

    @Column(name = "fuelConsumption")
    private double fuelConsumption;

    @Column(name = "consumptionDate")
    private String consumptionDate;

    @Column(name = "driverId")
    private String driverId;

    @Column(name = "totalAmount")
    private double totalAmount;

    public double getTotalAmount() {
        return (double) Math.round(totalAmount * 100) / 100;
    }

    public FuelConsumptionRequestEntity setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public String getFuelType() {
        return fuelType;
    }

    public FuelConsumptionRequestEntity setFuelType(String fuelType) {
        this.fuelType = fuelType;
        return this;
    }

    public double getPricePerLtr() {
        return pricePerLtr;
    }

    public FuelConsumptionRequestEntity setPricePerLtr(double pricePerLtr) {
        this.pricePerLtr = pricePerLtr;
        return this;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public FuelConsumptionRequestEntity setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        return this;
    }

    public String getConsumptionDate() {
        return consumptionDate;
    }

    public FuelConsumptionRequestEntity setConsumptionDate(String consumptionDate) {
        this.consumptionDate = consumptionDate;
        return this;
    }

    public String getDriverId() {
        return driverId;
    }

    public FuelConsumptionRequestEntity setDriverId(String driverId) {
        this.driverId = driverId;
        return this;
    }
}
