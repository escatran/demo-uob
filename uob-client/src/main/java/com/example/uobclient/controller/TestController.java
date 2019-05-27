package com.example.uobclient.controller;

import com.example.employee.dto.CheckEmployeeResult;
import com.example.employee.dto.EmployeeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/client")
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public String home() throws RestClientException, URISyntaxException {
        return restTemplate.getForObject(new URI("https://localhost:8000/ping"), String.class);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CheckEmployeeResult> createEmployee(@RequestBody EmployeeDto employeeDto) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String payload = objectMapper.writeValueAsString(employeeDto);
        HttpEntity<Object> request = new HttpEntity<Object>(payload, headers);

        ResponseEntity<CheckEmployeeResult> rspn = restTemplate.exchange("https://localhost:8000/emp", HttpMethod.POST, request, CheckEmployeeResult.class);
        return ResponseEntity.status(rspn.getStatusCode()).body(rspn.getBody());
    }
}
