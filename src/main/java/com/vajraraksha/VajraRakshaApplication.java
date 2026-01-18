package com.vajraraksha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VajraRakshaApplication {

    public static void main(String[] args) {
        // Triggering Rebuild
        SpringApplication.run(VajraRakshaApplication.class, args);
    }

}
