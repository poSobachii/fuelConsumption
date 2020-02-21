package com.fuel.fuelConsumption.service;

import com.fuel.fuelConsumption.entity.FuelConsumptionRequestEntity;
import com.fuel.fuelConsumption.exception.BadRequestException;
import com.fuel.fuelConsumption.repository.FuelRepository;
import com.fuel.fuelConsumption.request.FuelRegistrationRequest;
import com.fuel.fuelConsumption.response.EveryMonthSpentMoneyResponse;
import com.fuel.fuelConsumption.response.MonthlyResultIncomeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fuel.fuelConsumption.mapper.FuelConsumptionMapper.toEntity;
import com.fuel.fuelConsumption.rest.Utility;

@Service
public class FuelConsumptionService {

    @Autowired
    FuelRepository fuelRepository;

    public void createFuelConsumptionRequestAndSave(List<FuelRegistrationRequest> registrationRequest) {
        for (FuelRegistrationRequest entity : registrationRequest) {
            FuelConsumptionRequestEntity fuelConsumptionRequestEntits = toEntity(entity);
            fuelRepository.save(fuelConsumptionRequestEntits);
        }
    }


    public List<EveryMonthSpentMoneyResponse> calculateTotalSpendMoneyByMonth(List<String> driverIdList) {
        List<FuelConsumptionRequestEntity> fuelConsumptionList = new ArrayList<>();
        for (String id : driverIdList) {
            List<FuelConsumptionRequestEntity> byDriverId = fuelRepository.findListByDriverId(id);
            fuelConsumptionList.addAll(byDriverId);
        }
        if (fuelConsumptionList.size() == 0) {
            throw new BadRequestException("There is no any data for provided driver id, Please check the driver Id again");
        }

        Map<String, List<FuelConsumptionRequestEntity>> collect = fuelConsumptionList.stream().collect(Collectors.groupingBy(x -> x.getConsumptionDate().substring(0, 5)
                + x.getConsumptionDate().substring(5, 7)));

        Map<String, Double> fuelConsumptionTotalByMonth = fuelConsumptionList.stream().collect(Collectors.groupingBy(x -> x.getConsumptionDate().substring(0, 5)
                + x.getConsumptionDate().substring(5, 7), Collectors.summingDouble(FuelConsumptionRequestEntity::getTotalAmount)));

        return collect.entrySet()
                .parallelStream().map(entry -> {
                    double totalAmount = fuelConsumptionTotalByMonth.get(entry.getKey());
                    String date = entry.getKey();
                    return new EveryMonthSpentMoneyResponse(entry.getKey(), date, totalAmount);
                }).collect(Collectors.toList());
    }


    public List<FuelConsumptionRequestEntity> getMonthlyConsumptionByDriverId(String date, List<String> driverIdList) {
        LocalDate localDate = LocalDate.parse(date);
        LocalDate startDate = localDate.withDayOfMonth(1);
        LocalDate endDate = localDate.plusMonths(1).withDayOfMonth(1).minusDays(1);
        List<FuelConsumptionRequestEntity> consumptionRequestEntities = fuelRepository.findListByDate(String.valueOf(startDate), String.valueOf(endDate));
        if (consumptionRequestEntities.size() == 0) {
            throw new BadRequestException("There is no data for provided month with mentioned driver id");
        }
        return consumptionRequestEntities.stream()
                .filter(e -> driverIdList.stream().anyMatch(x -> x.equals(e.getDriverId())))
                .collect(Collectors.toList());
    }


    public Map<String, MonthlyResultIncomeResponse> getConsumptionMonthlyByFuelType(List<String> driverIdList) {
        List<FuelConsumptionRequestEntity> totalList = new ArrayList<>();
        for (String id : driverIdList) {
            List<FuelConsumptionRequestEntity> byDriverId = fuelRepository.findListByDriverId(id);
            totalList.addAll(byDriverId);
        }

        Map<String, Double> collectForPricePerLtr = totalList.stream().collect(Collectors.groupingBy(x -> x.getConsumptionDate().substring(0, 5)
                        + x.getConsumptionDate().substring(5, 7) + " FuelType " + x.getFuelType(),
                Collectors.averagingDouble(FuelConsumptionRequestEntity::getPricePerLtr)));

        if (collectForPricePerLtr.size() == 0) {
            throw new BadRequestException("There is no any data for provided driver id, Please check the driver id again");
        }

        Map<String, Double> collectForVolume = totalList.stream().collect(Collectors.groupingBy(x -> x.getConsumptionDate().substring(0, 5)
                        + x.getConsumptionDate().substring(5, 7) + " FuelType " + x.getFuelType(),
                Collectors.summingDouble(FuelConsumptionRequestEntity::getFuelConsumption)));

        Map<String, Double> collectForTotalPrice = totalList.stream().collect(Collectors.groupingBy(x -> x.getConsumptionDate().substring(0, 5)
                        + x.getConsumptionDate().substring(5, 7) + " FuelType " + x.getFuelType(),
                Collectors.summingDouble(FuelConsumptionRequestEntity::getTotalAmount)));


        return collectForPricePerLtr.entrySet()
                .parallelStream().map(entry -> {
                    double pricePerLtr = entry.getValue();
                    String fuelType = entry.getKey().substring(16).trim();
                    double fuelConsumption = collectForVolume.get(entry.getKey());
                    double totalPrice = collectForTotalPrice.get(entry.getKey());
                    return new MonthlyResultIncomeResponse(entry.getKey(), pricePerLtr, fuelType, fuelConsumption, totalPrice);

                }).collect(Collectors.toMap(MonthlyResultIncomeResponse::getKey, resp -> resp));
    }
}
