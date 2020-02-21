package com.fuel.fuelConsumption.mapper;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuel.fuelConsumption.entity.FuelConsumptionRequestEntity;
import com.fuel.fuelConsumption.request.FuelRegistrationRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import com.fuel.fuelConsumption.rest.Utility;

public class FuelConsumptionMapper {

    public static FuelConsumptionRequestEntity toEntity(FuelRegistrationRequest registrationRequest) {
        return new FuelConsumptionRequestEntity()
                .setFuelType(registrationRequest.getFuelType())
                .setPricePerLtr(registrationRequest.getPricePerLtr())
                .setFuelConsumption(registrationRequest.getFuelConsumption())
                .setConsumptionDate(registrationRequest.getDate())
                .setDriverId(registrationRequest.getDriverId())
                .setTotalAmount(registrationRequest.getPricePerLtr() * registrationRequest.getFuelConsumption());
    }

    public static List<FuelRegistrationRequest> FuelRegistrationLoadMapper(InputStream file, Class c) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, c);
            return mapper.readValue(file, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
