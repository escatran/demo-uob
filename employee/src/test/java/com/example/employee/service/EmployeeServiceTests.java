package com.example.employee.service;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.employee.router.EmployeeRouter.CHECK_EMPLOYEE_EXIST_ROUTE;
import static com.example.employee.router.EmployeeRouter.INVOKE_API_CHECK_EMPLOYEE_EXIST;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class EmployeeServiceTests {

    @Autowired
    private CamelContext camelContext;

    @EndpointInject(uri = "direct:dummyEndpoint")
    private ProducerTemplate producerTemplate;

    @EndpointInject(uri = "mock:callCheckExist")
    protected MockEndpoint mockEndpoint;


    @Test
    public void checkExist_EmployeeNotFound_ShouldReturnCreated() throws Exception {
        camelContext.getRouteDefinition(CHECK_EMPLOYEE_EXIST_ROUTE).adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                weaveById(INVOKE_API_CHECK_EMPLOYEE_EXIST).replace().to(mockEndpoint).setBody(mockResponse -> mockResponse);
            }
        });
    }

    @Test
    public void checkExist_EmployeeExist_ShouldReturnExist() {

    }
}
