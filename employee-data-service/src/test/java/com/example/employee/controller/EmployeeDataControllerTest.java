package com.example.employee.controller;

import com.example.employee.dto.EmployeeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeDataController.class)
public class EmployeeDataControllerTest {

    private static final boolean JSONASSERT_STRICTLY_COMPARE = true;
    private static final String DUMMY_VALUE = "DUMMY";

    private ObjectMapper objMper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void checkEmployeeExist_NoExist_ShouldReturnNotFound() throws Exception {
        EmployeeDto empl = EmployeeDto.builder()
                .mobile(DUMMY_VALUE)
                .gender(true)
                .email(DUMMY_VALUE)
                .firstName(DUMMY_VALUE)
                .lastName(DUMMY_VALUE)
                .build();
        String jsonRqst = objMper.writeValueAsString(empl);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/employee-data/exist")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonRqst);

        MvcResult result =  mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals("HTTP response code not matched.", HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void checkEmployeeExist_Exist_ShouldReturnOK() throws Exception {
        EmployeeDto empl = EmployeeDto.builder()
                .firstName("Jack")
                .lastName("Nicholson")
                .gender(true)
                .email("em1@comp.com")
                .mobile("001")
                .build();
        String jsonRqst = objMper.writeValueAsString(empl);
        String jsonRpsn = objMper.writeValueAsString(empl);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/employee-data/exist")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonRqst);

        MvcResult result =  mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals("HTTP response code not matched.", HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(jsonRpsn, response.getContentAsString(), JSONASSERT_STRICTLY_COMPARE);
    }
}