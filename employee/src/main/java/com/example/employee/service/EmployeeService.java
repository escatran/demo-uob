package com.example.employee.service;

import com.example.employee.dto.CheckEmployeeResult;
import com.example.employee.dto.EmployeeDto;

public interface EmployeeService {
    CheckEmployeeResult checkExist(EmployeeDto employeeDto);
}
