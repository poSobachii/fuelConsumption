package com.fuel.fuelConsumption;

import com.fuel.fuelConsumption.entity.FuelConsumptionRequestEntity;
import com.fuel.fuelConsumption.response.EveryMonthSpentMoneyResponse;
import com.fuel.fuelConsumption.response.MonthlyResultIncomeResponse;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {FuelConsumptionApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("IntegrationTest")
public class FuelConsumptionControllerIntegrationTest {

    @Value("${local.server.port}")
    protected int port;

    List<String> list = asList("1", "2", "3");

    private Response response;

    @Before
    public void basicSetUp() {
        RestAssured.port = this.port;
    }


    @Test
    public void uploadFile() throws IOException {
        InputStream inputStream = Files.newInputStream(Paths.get("hello.txt"));
        MultipartFile multipartFile = new MockMultipartFile("hello.txt", inputStream);
        response = RestAssured.given().accept(MediaType.MULTIPART_FORM_DATA).param("file", multipartFile).post("fuel/upload");
        JsonPath jsonPath = response.jsonPath();
        System.out.println(jsonPath);
    }

    @Test
    public void getTotalFuelConsumptionMonthly() {
        response = RestAssured.given().contentType(MediaType.APPLICATION_JSON).body(list).post("fuel/total");
        JsonPath jsonPath = response.jsonPath();
        List<EveryMonthSpentMoneyResponse> moneyResponseList = jsonPath.get();
        assertNotNull(moneyResponseList.size());
        assertTrue(moneyResponseList.size() > 1);
    }

    @Test
    public void getTotalFuelConsumptionMonthlyWithWrongDriverId() {
        response = RestAssured.given().contentType(MediaType.APPLICATION_JSON).body(asList("5")).post("fuel/total");
        JsonPath jsonPath = response.jsonPath();
        assertEquals("There is no any data for provided driver id, Please check the driver Id again", jsonPath.get("message"));
    }

    @Test
    public void getMonthlyReportUsingMonth() {
        response = RestAssured.given().contentType(MediaType.APPLICATION_JSON).body(list)
                .pathParam("date", "2018-01-12").post("fuel/month/{date}");
        JsonPath jsonPath = response.jsonPath();
        List<FuelConsumptionRequestEntity> entityList = jsonPath.get();
        assertTrue(entityList.size() > 1);
    }

    @Test
    public void getConsumptionMonthByFuelType() {
        response = RestAssured.given().body(list).contentType(MediaType.APPLICATION_JSON).post("fuel/consumption");
        JsonPath jsonPath = response.jsonPath();
        Map<String, MonthlyResultIncomeResponse> consumptionMap = jsonPath.get();
        assertTrue(consumptionMap.size() > 1);
    }
}
