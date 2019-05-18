package com.example.employee;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;

@SpringBootApplication
public class EmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

	class EmployeeCheckingRouteBuilder extends RouteBuilder {

		@Override
		public void configure() throws Exception {
			restConfiguration()
					.component("servlet")
					.bindingMode(RestBindingMode.json);

//			controller("/employee").produces(MediaType.APPLICATION_JSON.toString())
//					.post("/")
//						.to
		}
	}
}
