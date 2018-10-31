package com.fuel.fuelConsumption.service;

import com.fuel.fuelConsumption.entity.FuelConsumptionRequestEntity;
import com.fuel.fuelConsumption.exception.BadRequestException;
import com.fuel.fuelConsumption.repository.FuelRepository;
import com.fuel.fuelConsumption.request.FuelRegistrationRequest;
import com.fuel.fuelConsumption.response.EveryMonthSpentMoneyResponse;
import com.fuel.fuelConsumption.response.MonthlyResultIncomeResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static jdk.nashorn.internal.objects.NativeString.substring;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class FuelConsumptionServiceTest {
    @InjectMocks
    private FuelConsumptionService consumptionService;

    @Mock
    private FuelRepository fuelRepository;

    private List<FuelRegistrationRequest> requestList = new ArrayList<>();

    private List<FuelConsumptionRequestEntity> fuelEntityList = new ArrayList<>();

    @Before
    public void setUp() {
        requestList = getRequestList();
        fuelEntityList = getRequestEntityList();
    }

    @Test
    public void test(){
        String a = "123456789";
        System.out.println(substring(a,2,6));

    }
    @Test
    public void testCreateFuelConsumptionRequestAndSave() {
        FuelRegistrationRequest registrationRequest = new FuelRegistrationRequest()
                .setFuelType("97").setFuelConsumption(21.5).setPricePerLtr(11.5).setDate("2018-05-13").setDriverId("id");
        List<FuelRegistrationRequest> list = new ArrayList<>();
        list.add(registrationRequest);
        consumptionService.createFuelConsumptionRequestAndSave(list);
        Mockito.verify(fuelRepository).save(Mockito.any(FuelConsumptionRequestEntity.class));
    }

    @Test
    public void testCreateFuelConsumptionRequestAndSaveWithTwoRequest() {
        consumptionService.createFuelConsumptionRequestAndSave(requestList);
        Mockito.verify(fuelRepository, Mockito.times(4)).save(Mockito.any(FuelConsumptionRequestEntity.class));
    }

    @Test(expected = NullPointerException.class)
    public void testCreateFuelConsumptionRequestAndSaveWithNullPointerException() {
        consumptionService.createFuelConsumptionRequestAndSave(null);
    }

    @Test
    public void testTotalSpendMoneyByMonth() {
        when(fuelRepository.findListByDriverId("1")).thenReturn(fuelEntityList);
        when(fuelRepository.findListByDriverId("2")).thenReturn(fuelEntityList);
        List<EveryMonthSpentMoneyResponse> moneyResponseList = consumptionService.calculateTotalSpendMoneyByMonth(Arrays.asList("1", "2"));
        System.out.println(moneyResponseList);
        assertEquals("2018-07", String.valueOf(moneyResponseList.get(0).getConsumptionMonth()));
        assertEquals("544.46", String.valueOf(moneyResponseList.get(0).getTotalAmount()));
        assertEquals("3", String.valueOf(moneyResponseList.size()));
    }

    @Test(expected = BadRequestException.class)
    public void testTotalSpendMoneyByMonthUsingWrongID() {
        consumptionService.calculateTotalSpendMoneyByMonth(Arrays.asList("3"));
    }

    @Test
    public void testMonthlyConsumptionUsingWrongDriverId() {
        when(fuelRepository.findListByDate("2018-07-01", "2018-07-31")).thenReturn(fuelEntityList);
        List<FuelConsumptionRequestEntity> monthlyConsumptionByDriverId = consumptionService.getMonthlyConsumptionByDriverId("2018-07-15", Arrays.asList("3"));
        assertEquals("D", monthlyConsumptionByDriverId.get(0).getFuelType());
        assertEquals("2018-11-05", monthlyConsumptionByDriverId.get(0).getConsumptionDate());
        assertEquals("3", monthlyConsumptionByDriverId.get(0).getDriverId());
    }

    @Test
    public void testMonthlyConsumptionByDriverId() {
        when(fuelRepository.findListByDate("2018-07-01", "2018-07-31")).thenReturn(fuelEntityList);
        List<FuelConsumptionRequestEntity> monthlyConsumptionByDriverId = consumptionService.getMonthlyConsumptionByDriverId("2018-07-15", Arrays.asList("4"));
        assertEquals(Integer.parseInt("0"), monthlyConsumptionByDriverId.size());
    }

    @Test(expected = BadRequestException.class)
    public void testMonthlyConsumptionByNullList() {
        when(fuelRepository.findListByDate("2018-07-01", "2018-07-31")).thenReturn(Arrays.asList());
        consumptionService.getMonthlyConsumptionByDriverId("2018-07-15", Arrays.asList("4"));
    }

    @Test
    public void testConsumptionMonthlyByFuelType() {
//        Here i have only one list so if i put any id which is available then return the whole list, so don 't need
//        to do this ->when(fuelRepository.findListByDriverId("1")).thenReturn(fuelEntityList) operation for 2 and 3 id

        when(fuelRepository.findListByDriverId("1")).thenReturn(fuelEntityList);
        Map<String, MonthlyResultIncomeResponse> consumptionMonthlyByFuelType = consumptionService.getConsumptionMonthlyByFuelType(Arrays.asList("1", "2", "3"));
        assertEquals("D", consumptionMonthlyByFuelType.get("2018-07 FuelType D").getFuelType());
        assertEquals("10.06", String.valueOf(consumptionMonthlyByFuelType.get("2018-07 FuelType D").getPricePerLtr()));
        assertEquals("26.9", String.valueOf(consumptionMonthlyByFuelType.get("2018-07 FuelType D").getFuelConsumption()));
        assertEquals("272.23", String.valueOf(consumptionMonthlyByFuelType.get("2018-07 FuelType D").getTotalAmount()));
    }

    @Test(expected = BadRequestException.class)
    public void testConsumptionMonthlyByFuelTypeWithWrongDriverId() {
        consumptionService.getConsumptionMonthlyByFuelType(Arrays.asList("4"));
    }

    public List<FuelRegistrationRequest> getRequestList() {
        FuelRegistrationRequest request1 = new FuelRegistrationRequest()
                .setFuelType("97").setFuelConsumption(21.5).setPricePerLtr(11.5).setDate("2018-05-13").setDriverId("id");
        FuelRegistrationRequest request2 = new FuelRegistrationRequest()
                .setFuelType("D").setFuelConsumption(21.5).setPricePerLtr(11.5).setDate("2018-05-16").setDriverId("id");
        FuelRegistrationRequest request3 = new FuelRegistrationRequest()
                .setFuelType("97").setFuelConsumption(21.5).setPricePerLtr(11.5).setDate("2018-06-13").setDriverId("id");
        FuelRegistrationRequest request4 = new FuelRegistrationRequest()
                .setFuelType("97").setFuelConsumption(21.5).setPricePerLtr(11.5).setDate("2018-06-18").setDriverId("id");
        requestList.add(request1);
        requestList.add(request2);
        requestList.add(request3);
        requestList.add(request4);
        return requestList;
    }

    public List<FuelConsumptionRequestEntity> getRequestEntityList() {
        FuelConsumptionRequestEntity entity1 = new FuelConsumptionRequestEntity().setFuelType("D").setFuelConsumption(11.3).setPricePerLtr(10.12)
                .setConsumptionDate("2018-07-05").setDriverId("1").setTotalAmount(114.36);
        FuelConsumptionRequestEntity entity2 = new FuelConsumptionRequestEntity().setFuelType("97").setFuelConsumption(20.5).setPricePerLtr(9.5)
                .setConsumptionDate("2018-08-22").setDriverId("2").setTotalAmount(197.75);
        FuelConsumptionRequestEntity entity3 = new FuelConsumptionRequestEntity().setFuelType("98").setFuelConsumption(14.3).setPricePerLtr(5.9)
                .setConsumptionDate("2018-08-05").setDriverId("2").setTotalAmount(115);
        FuelConsumptionRequestEntity entity4 = new FuelConsumptionRequestEntity().setFuelType("D").setFuelConsumption(15.6).setPricePerLtr(10)
                .setConsumptionDate("2018-07-19").setDriverId("2").setTotalAmount(157.87);
        FuelConsumptionRequestEntity entity5 = new FuelConsumptionRequestEntity().setFuelType("D").setFuelConsumption(15.6).setPricePerLtr(10)
                .setConsumptionDate("2018-11-05").setDriverId("3").setTotalAmount(157.87);
        fuelEntityList.add(entity1);
        fuelEntityList.add(entity2);
        fuelEntityList.add(entity3);
        fuelEntityList.add(entity4);
        fuelEntityList.add(entity5);
        return fuelEntityList;
    }
}
