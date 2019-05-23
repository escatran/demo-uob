package com.example.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeDto {
    @NotNull(message = "Please provide firstName")
    private String firstName;

    @NotNull(message = "Please provide lastName")
    private String lastName;

    @NotNull(message = "Please provide gender")
    private Boolean gender;

    @NotNull(message = "Please provide email")
    private String email;

    @NotNull(message = "Please provide mobile")
    private String mobile;
}
