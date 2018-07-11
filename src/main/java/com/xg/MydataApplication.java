package com.xg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MydataApplication {

	public static void main(String[] args) {
		SpringApplication.run(MydataApplication.class, args);
	}
}
