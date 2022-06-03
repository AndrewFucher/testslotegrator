package com.testproject.testproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class TestprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestprojectApplication.class, args);
	}

}
