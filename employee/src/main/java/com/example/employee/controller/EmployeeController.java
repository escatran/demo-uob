package com.example.employee.controller;

import com.example.employee.dto.CheckEmployeeResult;
import com.example.employee.dto.EmployeeCheckingStatus;
import com.example.employee.dto.EmployeeDto;
import com.example.employee.dto.UserDto;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RequestMapping("/emp")
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(path = "/exist", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CheckEmployeeResult> checkExist(@RequestBody @Valid EmployeeDto employee) {
        CheckEmployeeResult result = employeeService.checkExist(employee);
        return result.getStatus() == EmployeeCheckingStatus.CREATED
                ? ResponseEntity.status(HttpStatus.CREATED).body(result) : ResponseEntity.ok(result);
    }

    @GetMapping
    public String hello() {
        return "Hello World!!! " + new Date().toString();
    }
}
