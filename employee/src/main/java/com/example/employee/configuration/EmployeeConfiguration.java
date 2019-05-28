package com.example.employee.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("employee-data-service")
@Data
public class EmployeeConfiguration {

    private Paths paths;

    @Data
    public static class Paths {
        private String camelURICheckExist;
        private String camelURICreateNew;
    }
}
