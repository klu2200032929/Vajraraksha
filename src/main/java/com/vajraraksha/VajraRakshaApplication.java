package com.vajraraksha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class VajraRakshaApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(VajraRakshaApplication.class, args);
            System.out.println("Application started successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
