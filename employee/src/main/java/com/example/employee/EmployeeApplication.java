package com.example.employee;

import com.example.employee.dto.Student;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
public class EmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

//	@Component
	class EmployeeCheckingRouteBuilder extends RouteBuilder {

		public static final String TO_EMPLOYEE_DATA_SERVICE_CHECK_EXIST = "direct:checkExist";

		@Override
		public void configure() throws Exception {
			restConfiguration()
					.component("servlet")
					.bindingMode(RestBindingMode.json);

			rest("/employee")
					.produces(MediaType.APPLICATION_JSON.toString())
					.consumes(MediaType.APPLICATION_JSON.toString())
					.post("/")
					.route().to(TO_EMPLOYEE_DATA_SERVICE_CHECK_EXIST);

			from(TO_EMPLOYEE_DATA_SERVICE_CHECK_EXIST)
					.setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST.toString()))
					.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON_VALUE))
					.process(exchange -> {
						System.out.println(exchange);
					})
					.to("http://localhost:8090/employee-data/exist")
					.process(exchange -> {
						int statusCode = exchange.getOut().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
					});
//			rest("/student").produces("application/json")
//					.get("/hello/{name}")
//					.route().transform().simple("Hello ${header.name}, Welcome to JavaOutOfBounds.com")
//					.endRest()
//					.get("/records/{name}").to("direct:records");
//
//			from("direct:records")
//					.process(new Processor() {
//
//						final AtomicLong counter = new AtomicLong();
//
//						@Override
//						public void process(Exchange exchange) throws Exception {
//
//							final String name = exchange.getIn().getHeader("name",String.class);
//							exchange.getIn().setBody(new Student(counter.incrementAndGet(),name,"Camel + SpringBoot"));
//						}
//					});
		}
	}
}
