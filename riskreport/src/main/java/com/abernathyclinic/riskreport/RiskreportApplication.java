package com.abernathyclinic.riskreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RiskreportApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiskreportApplication.class, args);
	}

}
