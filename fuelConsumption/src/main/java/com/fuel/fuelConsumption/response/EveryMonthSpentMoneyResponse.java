package com.fuel.fuelConsumption.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static com.fuel.fuelConsumption.response.MonthlyResultIncomeResponse.df;
import com.fuel.fuelConsumption.rest.Utility;

public class EveryMonthSpentMoneyResponse {
    @JsonIgnore
    private String key;
    private String consumptionMonth;
    private double totalAmount;

    public EveryMonthSpentMoneyResponse(String key, String usedDate, double totalAmount) {
        this.key = key;
        this.consumptionMonth = usedDate;
        this.totalAmount = totalAmount;
    }

    public EveryMonthSpentMoneyResponse(String usedDate, double totalAmount) {
        this.consumptionMonth = usedDate;
        this.totalAmount = totalAmount;
    }

    public String getConsumptionMonth() {
        return consumptionMonth;
    }

    public void setConsumptionMonth(String consumptionMonth) {
        this.consumptionMonth = consumptionMonth;
    }

    public double getTotalAmount() {
        return Double.valueOf(df.format(totalAmount));
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
