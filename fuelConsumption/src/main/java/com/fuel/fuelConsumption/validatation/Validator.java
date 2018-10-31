package com.fuel.fuelConsumption.validatation;

import com.fuel.fuelConsumption.exception.BadRequestException;
import com.fuel.fuelConsumption.request.FuelRegistrationRequest;
import org.springframework.util.StringUtils;

import java.util.List;

public class Validator {

    public static void validate(List<FuelRegistrationRequest> fuelRegistrationRequest) {

        for (FuelRegistrationRequest registrationRequest : fuelRegistrationRequest) {
            if (StringUtils.isEmpty(registrationRequest)) {
                throw new BadRequestException("Your input value is empty or null");
            }
            checkMandatoryParameter("fuelType", registrationRequest.getFuelType());
            checkMandatoryParameter("pricePerLtr", String.valueOf(registrationRequest.getPricePerLtr()));
            checkMandatoryParameter("fuelConsumption", String.valueOf(registrationRequest.getFuelConsumption()));
            checkMandatoryParameter("date", registrationRequest.getDate());
            checkMandatoryParameter("driverId", registrationRequest.getDriverId());

            if ((!"D".equals(registrationRequest.getFuelType()) && !"97".equals(registrationRequest.getFuelType()) && !"98".equals(registrationRequest.getFuelType()))) {
                throw new BadRequestException("Fuel type is only D, 97 and 98, please choose the correct values");
            }

            checkDate(registrationRequest.getDate());
        }
    }

    public static void checkDate(String date) {
        if (!date.matches("^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")) {
            throw new BadRequestException("Please use the date in yyyy-mm-dd format, example: 2018-01-21");

        }
    }

    public static void checkList(List<String> driverIdList) {
        for (String driverId : driverIdList) {
            if (StringUtils.isEmpty(driverId)) {
                throw new BadRequestException("Your input value is empty or null");
            }
        }
    }

    public static void checkMandatoryParameter(final String name, final String value) {
        if (null == value || value.trim().isEmpty() || "0.0".equals(value)) {
            throw new BadRequestException(name + "is mandatory");
        }
    }
}
