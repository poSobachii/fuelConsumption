package com.fuel.fuelConsumption.rest;


import com.fuel.fuelConsumption.entity.FuelConsumptionRequestEntity;
import com.fuel.fuelConsumption.repository.FuelRepository;
import com.fuel.fuelConsumption.request.FuelRegistrationRequest;
import com.fuel.fuelConsumption.response.EveryMonthSpentMoneyResponse;
import com.fuel.fuelConsumption.response.MonthlyResultIncomeResponse;
import com.fuel.fuelConsumption.service.FuelConsumptionService;
import com.fuel.fuelConsumption.validatation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static com.fuel.fuelConsumption.mapper.FuelConsumptionMapper.FuelRegistrationLoadMapper;


@RestController
@RequestMapping("fuel")
public class FuelConsumptionController {

    @Autowired
    FuelConsumptionService consumptionService;

    @Autowired
    FuelRepository fuelRepository;

    /**
     * Method to create fuel consumption request
     */

    @RequestMapping(value = "upload", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA})
    public void createRequestForFuelConsumption(@RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            List<FuelRegistrationRequest> fuelRegistrationRequests = FuelRegistrationLoadMapper(inputStream, FuelRegistrationRequest.class);
            Validator.validate(fuelRegistrationRequests);
            consumptionService.createFuelConsumptionRequestAndSave(fuelRegistrationRequests);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method to calculate total spent amount of money grouped by month driverId
     */

    @RequestMapping(value = "total", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public List<EveryMonthSpentMoneyResponse> findTotalMoneyGroupByMonth(@RequestBody List<String> driverIdList) {
        Validator.checkList(driverIdList);
        return consumptionService.calculateTotalSpendMoneyByMonth(driverIdList);
    }


    /**
     * Method to calculate consumption records specified by month and driverId
     */

    @RequestMapping(value = "month/{date}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
//            consumes = {MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA})
    public List<FuelConsumptionRequestEntity> findMonthlyConsumption(@PathVariable String date, @RequestBody List<String> driverIdList) {
        Validator.checkDate(date);
        Validator.checkList(driverIdList);
        return consumptionService.getMonthlyConsumptionByDriverId(date, driverIdList);
    }


    /**
     * Method to calculate fuel consumption group by month and fuel type by using driverId
     */

    @RequestMapping(value = "/consumption", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON,
            consumes = {MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA})
    public Map<String, MonthlyResultIncomeResponse> findMonthlyConsumptionRecordByFuelType(@RequestBody List<String> driverIdList) {
        Validator.checkList(driverIdList);
        return consumptionService.getConsumptionMonthlyByFuelType(driverIdList);
    }

    @RequestMapping(value = "/findall", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public List<FuelConsumptionRequestEntity> getAll() {
        return fuelRepository.findAll();
    }

    @RequestMapping(value = "/deleteall", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
    public void deleteAll() {
        fuelRepository.flush();
        fuelRepository.deleteAll();
    }
}