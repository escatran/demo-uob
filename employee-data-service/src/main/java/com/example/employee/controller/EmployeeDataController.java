package com.example.employee.controller;

import com.example.employee.dto.EmployeeDto;
import com.example.employee.exception.BusinessException;
import com.example.employee.exception.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/employee-data")
@RestController
public class EmployeeDataController {

    private static final Map<String, EmployeeDto> DATA = new HashMap<>();

    static {
        Arrays.asList(
                EmployeeDto.builder()
                    .firstName("Jack")
                    .lastName("Nicholson")
                    .gender(true)
                    .email("em1@comp.com")
                    .mobile("001")
                    .build(),
                EmployeeDto.builder()
                    .firstName("Monica")
                    .lastName("Lewinsky")
                    .gender(false)
                    .email("em2@comp.com")
                    .mobile("002")
                    .build(),
                EmployeeDto.builder()
                    .firstName("Bill")
                    .lastName("Gehard")
                    .gender(true)
                    .email("em3@comp.com")
                    .mobile("003")
                    .build()
        ).stream().forEach(row -> DATA.put(row.getMobile(), row));

    }

    @PostMapping(path = "/exist", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto checkEmployeeExist(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto emp = DATA.get(employeeDto.getMobile());
        if (emp == null) {
            throw new ResourceNotFoundException("Employee not found");
        }
        return emp;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
        DATA.put(employeeDto.getMobile(), employeeDto);
        return employeeDto;
    }
}
