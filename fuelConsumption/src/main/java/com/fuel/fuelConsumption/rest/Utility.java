package com.fuel.fuelConsumption.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class Utility {

    @Bean
    public static String init(){
        TimeCheck("---> THIS IS A BEAN CREATION TEST  + @Configuration annotation check <---");
        return null;
    }

    public static void TimeCheck(String text) {
        long duration = System.currentTimeMillis();
        DateFormat simple = new SimpleDateFormat("HH:mm:ss:SSS");
        Date result = new Date(duration);
        System.out.println( simple.format(result) + " " + text);
    }

}
