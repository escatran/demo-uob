package com.example.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private Boolean gender;
    private String email;
    private String mobile;
}
