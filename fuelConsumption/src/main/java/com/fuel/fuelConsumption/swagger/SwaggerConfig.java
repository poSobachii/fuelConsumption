package com.fuel.fuelConsumption.swagger;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fuel.fuelConsumption.rest.Utility;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket fuelApi() {
        Utility.TimeCheck("SwaggerBean involved");
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fuel.fuelConsumption.rest"))
                .paths(any())
                .build();
    }
}
