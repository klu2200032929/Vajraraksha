package com.vajraraksha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VajraRakshaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VajraRakshaApplication.class, args);
		System.out.println("\n\n===========================================================");
		System.out.println("  VajraRaksha Application Started Successfully!");
		System.out.println("  Access URL: http://localhost:8081/");
		System.out.println("===========================================================\n");
	}
}
