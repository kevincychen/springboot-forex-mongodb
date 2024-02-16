package com.wits.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootForexMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootForexMongodbApplication.class, args);
	}

}
