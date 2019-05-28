package com.example.uobclient.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("employee")
@Data
public class EmployeeServiceConfigs {
    private Paths paths;
    private SSLConfigs sslConfigs;

    @Data
    public static class Paths {
        private String checkEmployeeExists;
        private String ping;
    }

    @Data
    private static class SSLConfigs {
        private String keyStorePassword;
        private String trustStorePassword;
    }
}
