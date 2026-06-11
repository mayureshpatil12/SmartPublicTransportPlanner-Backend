package com.mayuresh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SmartPublicTransportPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartPublicTransportPlannerApplication.class, args);
	}

}
