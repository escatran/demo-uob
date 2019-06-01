package com.example.employee.router;

import com.example.employee.configuration.EmployeeConfiguration;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;


@Component
public class EmployeeRouter extends RouteBuilder {

    public static final String EMPLOYEE_DATA_SERVICE_CHECK_EXIST = "direct:checkExist";
    public static final String CHECK_EMPLOYEE_EXIST_ROUTE = "CHECK_EMPLOYEE_EXIST_ROUTE";
    public static final String INVOKE_API_CHECK_EMPLOYEE_EXIST = "INVOKE_API_CHECK_EMPLOYEE_EXIST";
    public static final String INVOKE_API_CREATE_EMPLOYEE = "INVOKE_API_CREATE_EMPLOYEE";

    @Autowired
    private EmployeeConfiguration employeeConfigs;

    @Override
    public void configure() throws Exception {
        from(EMPLOYEE_DATA_SERVICE_CHECK_EXIST)
            .routeId(CHECK_EMPLOYEE_EXIST_ROUTE)
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST.toString()))
                .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON_VALUE))
                .marshal().json(JsonLibrary.Jackson)
            .to(employeeConfigs.getPaths().getCamelURICheckExist()).id(INVOKE_API_CHECK_EMPLOYEE_EXIST)
                .choice()
                    .when(header(Exchange.HTTP_RESPONSE_CODE).isEqualTo(HttpStatus.NOT_FOUND.value()))
                        .process(exchange -> exchange.setIn(exchange.getUnitOfWork().getOriginalInMessage()))
                        .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST.toString()))
                        .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON_VALUE))
                        .marshal().json(JsonLibrary.Jackson)
                        .to(employeeConfigs.getPaths().getCamelURICreateNew()).id(INVOKE_API_CREATE_EMPLOYEE)
                .end();
    }
}
