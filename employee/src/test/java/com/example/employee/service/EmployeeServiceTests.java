package com.example.employee.service;

import com.example.employee.dto.EmployeeDto;
import com.example.employee.service.impl.EmployeeServiceImpl;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.employee.router.EmployeeRouter.CHECK_EMPLOYEE_EXIST_ROUTE;
import static com.example.employee.router.EmployeeRouter.INVOKE_API_CHECK_EMPLOYEE_EXIST;
import static com.example.employee.router.EmployeeRouter.INVOKE_API_CREATE_EMPLOYEE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class EmployeeServiceTests {

    private static final String DUMMY_VALUE = "DUMMY_VALUE";

    @Autowired
    private CamelContext camelContext;

    @EndpointInject(uri = "direct:dummyEndpoint")
    private ProducerTemplate producerTemplate;

    @EndpointInject(uri = "mock:callCheckExist")
    protected MockEndpoint mockEndpointCheckExist;

    @EndpointInject(uri = "mock:createEmployee")
    protected MockEndpoint mockEndpointCreateEmployee;

    private EmployeeServiceImpl employeeService;


    @Test
    public void checkExist_EmployeeNotFound_ShouldReturnCreated() throws Exception {
        EmployeeDto dummyEmployeeDto = EmployeeDto.builder()
                .firstName(DUMMY_VALUE)
                .lastName(DUMMY_VALUE)
                .email(DUMMY_VALUE)
                .gender(true)
                .mobile(DUMMY_VALUE)
                .build();
        camelContext.getRouteDefinition(CHECK_EMPLOYEE_EXIST_ROUTE).adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                weaveById(INVOKE_API_CHECK_EMPLOYEE_EXIST).replace().process(exchange -> {
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.NOT_FOUND.value());
                    exchange.getOut().setBody(dummyEmployeeDto);
                });

                weaveById(INVOKE_API_CREATE_EMPLOYEE).replace().to(mockEndpointCreateEmployee)
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, () -> HttpStatus.CREATED.value());
            }
        });
        employeeService = new EmployeeServiceImpl(producerTemplate, camelContext);


        employeeService.checkExist(dummyEmployeeDto);


        mockEndpointCreateEmployee.expectedMessageCount(1);
        mockEndpointCreateEmployee.expectedBodiesReceived(dummyEmployeeDto);
    }

    @Test
    public void checkExist_EmployeeExist_ShouldReturnExist() {

    }
}
