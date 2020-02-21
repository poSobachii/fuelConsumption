package com.fuel.fuelConsumption;

import com.fuel.fuelConsumption.rest.Utility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class FuelConsumptionApplication {

	public static void main(String[] args) {
		Utility.TimeCheck("Application START");
		SpringApplication.run(FuelConsumptionApplication.class, args);
	}
}
