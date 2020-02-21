package com.fuel.fuelConsumption.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.DecimalFormat;
import com.fuel.fuelConsumption.rest.Utility;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MonthlyResultIncomeResponse {
    public static DecimalFormat df = new DecimalFormat("####0.00");

    @JsonIgnore
    private String key;
    private String fuelType;
    private double pricePerLtr;
    private double fuelConsumption;
    private double totalAmount;

    public MonthlyResultIncomeResponse(String key, double pricePerLtr, String fuelType, double fuelConsumption, double totalAmount) {
        this.key = key;
        this.fuelType = fuelType;
        this.pricePerLtr = pricePerLtr;
        this.fuelConsumption = fuelConsumption;
        this.totalAmount = totalAmount;
    }

    public String getKey() {
        return key;
    }

    public MonthlyResultIncomeResponse setKey(String key) {
        this.key = key;
        return this;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public double getPricePerLtr() {
        return Double.valueOf(df.format(pricePerLtr));
    }

    public MonthlyResultIncomeResponse setPricePerLtr(double pricePerLtr) {
        this.pricePerLtr = pricePerLtr;
        return this;
    }

    public double getFuelConsumption() {
        return Double.valueOf(df.format(fuelConsumption));
    }

    public MonthlyResultIncomeResponse setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        return this;
    }

    public double getTotalAmount() {
        return Double.valueOf(df.format(totalAmount));
    }

    public MonthlyResultIncomeResponse setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }
}
