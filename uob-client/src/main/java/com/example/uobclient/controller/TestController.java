package com.example.uobclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/client")
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String home() throws RestClientException, URISyntaxException {
        return restTemplate.getForObject(new URI("https://localhost:8000/ping"), String.class);
    }
}
