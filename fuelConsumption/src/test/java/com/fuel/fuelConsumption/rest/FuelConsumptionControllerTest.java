//package com.fuel.fuelConsumption.rest;
//
//import com.fuel.fuelConsumption.entity.FuelConsumptionRequestEntity;
//import com.fuel.fuelConsumption.exception.BadRequestException;
//import com.fuel.fuelConsumption.response.EveryMonthSpentMoneyResponse;
//import com.fuel.fuelConsumption.response.MonthlyResultIncomeResponse;
//import com.fuel.fuelConsumption.service.FuelConsumptionService;
//import com.fuel.fuelConsumption.service.FuelConsumptionServiceTest;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.*;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class FuelConsumptionControllerTest {
//
//    @InjectMocks
//    private FuelConsumptionController consumptionController;
//
//    @Mock
//    private FuelConsumptionService consumptionService;
//
//    private FuelConsumptionServiceTest serviceTest = new FuelConsumptionServiceTest();
//
//
//    @Test
//    public void createRequestForFuelConsumption() throws IOException {
//        InputStream inputStream = Files.newInputStream(Paths.get("hello.txt"));
//        MultipartFile multipartFile = new MockMultipartFile("hello.txt", inputStream);
//        consumptionController.createRequestForFuelConsumption(multipartFile);
//        verify(consumptionService).createFuelConsumptionRequestAndSave(Mockito.anyList());
//    }
//
//    @Test(expected = BadRequestException.class)
//    public void createRequestForFuelConsumptionWithWrongFuelType() throws IOException {
//        InputStream inputStream = Files.newInputStream(Paths.get("wrongFuelType.txt"));
//        MultipartFile multipartFile = new MockMultipartFile("wrongFuelType.txt", inputStream);
//        consumptionController.createRequestForFuelConsumption(multipartFile);
//    }
//
//    @Test(expected = BadRequestException.class)
//    public void createRequestForFuelConsumptionWithoutPricePerLtr() throws IOException {
//        InputStream inputStream = Files.newInputStream(Paths.get("pricePerLiterNotavailable.txt"));
//        MultipartFile multipartFile = new MockMultipartFile("pricePerLiterNotavailable.txt", inputStream);
//        consumptionController.createRequestForFuelConsumption(multipartFile);
//    }
//
//    @Test(expected = BadRequestException.class)
//    public void createRequestForFuelConsumptionWithWrongDateFormat() throws IOException {
//        InputStream inputStream = Files.newInputStream(Paths.get("wrongDateFormat.txt"));
//        MultipartFile multipartFile = new MockMultipartFile("wrongDateFormat.txt", inputStream);
//        consumptionController.createRequestForFuelConsumption(multipartFile);
//    }
//
//    @Test
//    public void testTotalMoneyGroupByMonth() {
//        List<EveryMonthSpentMoneyResponse> totalMoneyList = new ArrayList<>();
//        EveryMonthSpentMoneyResponse response1 = new EveryMonthSpentMoneyResponse("2018-02", 879.5);
//        EveryMonthSpentMoneyResponse response2 = new EveryMonthSpentMoneyResponse("2018-11", 500.54);
//        totalMoneyList.add(response1);
//        totalMoneyList.add(response2);
//        when(consumptionService.calculateTotalSpendMoneyByMonth(Arrays.asList("1", "2"))).thenReturn(totalMoneyList);
//        List<EveryMonthSpentMoneyResponse> totalMoneyGroupByMonth = consumptionController.findTotalMoneyGroupByMonth(Arrays.asList("1", "2"));
//        System.out.println(totalMoneyGroupByMonth);
//        assertEquals("879.5", String.valueOf(totalMoneyGroupByMonth.get(0).getTotalAmount()));
//        assertEquals("2018-02", String.valueOf(totalMoneyGroupByMonth.get(0).getConsumptionMonth()));
//        assertEquals("500.54", String.valueOf(totalMoneyGroupByMonth.get(1).getTotalAmount()));
//        assertEquals("2018-11", String.valueOf(totalMoneyGroupByMonth.get(1).getConsumptionMonth()));
//    }
//
//    @Test(expected = BadRequestException.class)
//    public void testTotalMoneyGroupByMonthUsingEmptyDriverId() {
//        consumptionController.findTotalMoneyGroupByMonth(Arrays.asList(""));
//    }
//
//    @Test
//    public void testMonthlyConsumption() {
//        List<FuelConsumptionRequestEntity> entityList = serviceTest.getRequestEntityList();
//        when(consumptionService.getMonthlyConsumptionByDriverId("2018-07-04", Arrays.asList("1"))).thenReturn(entityList);
//        List<FuelConsumptionRequestEntity> monthlyConsumption = consumptionController.findMonthlyConsumption("2018-07-04", Arrays.asList("1"));
//        assertEquals("D", monthlyConsumption.get(0).getFuelType());
//        assertEquals("2018-07-05", monthlyConsumption.get(0).getConsumptionDate());
//        assertEquals("1", monthlyConsumption.get(0).getDriverId());
//        assertEquals(5, monthlyConsumption.size());
//    }
//
//    @Test(expected = BadRequestException.class)
//    public void testMonthlyConsumptionWithWrongDateFormat() {
//        consumptionController.findMonthlyConsumption("2018.12.04", Arrays.asList("1"));
//    }
//
//    @Test(expected = BadRequestException.class)
//    public void testMonthlyConsumptionWithWrongOrderOfMonthAndDate() {
//        consumptionController.findMonthlyConsumption("2018-21-04", Arrays.asList("1"));
//    }
//
//    @Test(expected = BadRequestException.class)
//    public void testMonthlyConsumptionUsingEmptyDriverId() {
//        consumptionController.findMonthlyConsumption("2018-21-04", Arrays.asList(""));
//    }
//
//    @Test
//    public void testMonthlyConsumptionRecordByFuelType() {
//        MonthlyResultIncomeResponse monthlyResultIncomeResponse = new MonthlyResultIncomeResponse("2018-05", 11.5, "D", 21.5, 247.25);
//        Map<String, MonthlyResultIncomeResponse> responseMap = new HashMap<>();
//        responseMap.put("2018-05 FuelType D", monthlyResultIncomeResponse);
//        when(consumptionService.getConsumptionMonthlyByFuelType(Arrays.asList("1"))).thenReturn(responseMap);
//        Map<String, MonthlyResultIncomeResponse> monthlyConsumptionRecordByFuelType = consumptionController.findMonthlyConsumptionRecordByFuelType(Arrays.asList("1"));
//        assertEquals("D", monthlyConsumptionRecordByFuelType.get("2018-05 FuelType D").getFuelType());
//        assertEquals("11.5", String.valueOf(monthlyConsumptionRecordByFuelType.get("2018-05 FuelType D").getPricePerLtr()));
//        assertEquals("21.5", String.valueOf(monthlyConsumptionRecordByFuelType.get("2018-05 FuelType D").getFuelConsumption()));
//        assertEquals("247.25", String.valueOf(monthlyConsumptionRecordByFuelType.get("2018-05 FuelType D").getTotalAmount()));
//    }
//}
