package com.example.employeezuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class EmployeeZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeZuulApplication.class, args);
	}

}
