package com.example.employee.service.impl;

import com.example.employee.dto.CheckEmployeeResult;
import com.example.employee.dto.EmployeeCheckingStatus;
import com.example.employee.dto.EmployeeDto;
import com.example.employee.exception.BusinessException;
import com.example.employee.router.EmployeeRouter;
import com.example.employee.service.EmployeeService;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private ProducerTemplate producerTemplate;
    private CamelContext camelContext;

    @Autowired
    public EmployeeServiceImpl(ProducerTemplate producerTemplate, CamelContext camelContext) {
        this.producerTemplate = producerTemplate;
        this.camelContext = camelContext;
    }

    @Override
    public CheckEmployeeResult checkExist(EmployeeDto employeeDto) {
        camelContext.setAllowUseOriginalMessage(true);
        Exchange requestExchange = ExchangeBuilder.anExchange(camelContext).withBody(employeeDto).build();
        Exchange responseExchange = producerTemplate.send(EmployeeRouter.EMPLOYEE_DATA_SERVICE_CHECK_EXIST, requestExchange);
        int responseCode = responseExchange.getMessage().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class).intValue();
        if (responseCode == HttpStatus.SC_CREATED) {
            return CheckEmployeeResult.builder()
                    .mobile(employeeDto.getMobile())
                    .status(EmployeeCheckingStatus.CREATED)
                    .build();
        } else if (responseCode == HttpStatus.SC_OK) {
            return CheckEmployeeResult.builder()
                    .mobile(employeeDto.getMobile())
                    .status(EmployeeCheckingStatus.ALREADY_EXIST)
                    .build();
        }
        throw new BusinessException("Unexpected response from employee-data-service.");
    }
}
