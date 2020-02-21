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
import com.fuel.fuelConsumption.rest.Utility;

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
        Utility.TimeCheck("reqmap - UPLOAD - requsted");
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
        Utility.TimeCheck("reqmap - TOTAL - requsted");
        Validator.checkList(driverIdList);
        return consumptionService.calculateTotalSpendMoneyByMonth(driverIdList);
    }


    /**
     * Method to calculate consumption records specified by month and driverId
     */

    @RequestMapping(value = "month/{date}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
//            consumes = {MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA})
    public List<FuelConsumptionRequestEntity> findMonthlyConsumption(@PathVariable String date, @RequestBody List<String> driverIdList) {
        Utility.TimeCheck("reqmap - MONTH.DATE - requsted");
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
        Utility.TimeCheck("reqmap - CONSUMPTION - requsted");
        Validator.checkList(driverIdList);
        return consumptionService.getConsumptionMonthlyByFuelType(driverIdList);
    }

    @RequestMapping(value = "/findall", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public List<FuelConsumptionRequestEntity> getAll() {
        Utility.TimeCheck("reqmap - FINDALL - requsted");
        return fuelRepository.findAll();
    }

    @RequestMapping(value = "/deleteall", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public void deleteAll() {
        Utility.TimeCheck("reqmap - DELETEALL - requsted");
        fuelRepository.flush();
        fuelRepository.deleteAll();
    }
    @RequestMapping(value = "/checkme", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public void checkMe() {
        Utility.TimeCheck("reqmap - CHECK.ME - requsted");
    }
    @RequestMapping(value = "/wwwPOScheckme", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public void WWWPOSTcheckMe() {
        Utility.TimeCheck("reqmap - WWW_POST_CHECK.ME - requsted");
    }

    @RequestMapping(value = "/wwwGETcheckme", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public String WWWGETcheckMe() {
        Utility.TimeCheck("reqmap - WWW_GET_CHECK.ME - requsted");
        return "Hello GET METHOD";   // only GET can return smth in browser
    }
    @RequestMapping(value = "/wwwDELcheckme", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
    public void WWWDELETEcheckMe() {
        Utility.TimeCheck("reqmap - WWW_DELETE_CHECK.ME - requsted");
    }



//    @RequestMapping(value = "/wwwHEDcheckme", method = RequestMethod.HEAD, produces = MediaType.APPLICATION_JSON)
//    public void WWWHEADcheckMe() {
//        Utility.TimeCheck("reqmap - WWW_HEAD_CHECK.ME - requsted");
//    }
//    @RequestMapping(value = "/wwwPUTcheckme", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
//    public void WWWPUTcheckMe() {
//        Utility.TimeCheck("reqmap - WWW_PUT_CHECK.ME - requsted");
//    }
//    @RequestMapping(value = "/wwwPATcheckme", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON)
//    public void WWWPATCHcheckMe() {
//        Utility.TimeCheck("reqmap - WWW_PATCH_CHECK.ME - requsted");
//    }
//    @RequestMapping(value = "/wwwOPTcheckme", method = RequestMethod.OPTIONS, produces = MediaType.APPLICATION_JSON)
//    public void WWWOPTIONScheckMe() {
//        Utility.TimeCheck("reqmap - WWW_OPTIONS_CHECK.ME - requsted");
//    }
//    @RequestMapping(value = "/wwwTRAcheckme", method = RequestMethod.TRACE, produces = MediaType.APPLICATION_JSON)
//    public void WWWTRACEcheckMe() {
//        Utility.TimeCheck("reqmap - WWW_TRACE_CHECK.ME - requsted");
//    }
}