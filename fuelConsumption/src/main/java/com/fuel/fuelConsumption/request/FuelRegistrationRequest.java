package com.fuel.fuelConsumption.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FuelRegistrationRequest {
    private String fuelType;
    private double pricePerLtr;
    private double fuelConsumption;
    private String date;
    private String driverId;

    public String getFuelType() {
        return fuelType;
    }

    public FuelRegistrationRequest setFuelType(String fuelType) {
        this.fuelType = fuelType;
        return this;
    }

    public double getPricePerLtr() {
        return pricePerLtr;
    }

    public FuelRegistrationRequest setPricePerLtr(double pricePerLtr) {
        this.pricePerLtr = pricePerLtr;
        return this;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public FuelRegistrationRequest setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        return this;
    }

    public String getDate() {
        return date;
    }

    public FuelRegistrationRequest setDate(String date) {
        this.date = date;
        return this;
    }

    public String getDriverId() {
        return driverId;
    }

    public FuelRegistrationRequest setDriverId(String driverId) {
        this.driverId = driverId;
        return this;
    }
}
