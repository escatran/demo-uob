package com.example.employee.controller;

import com.example.employee.dto.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @PostMapping
    public UserDto checkUser(@RequestBody UserDto user) {
        return null;
    }
}
