package com.example.employee.controller;

import com.example.employee.dto.CheckEmployeeResult;
import com.example.employee.dto.EmployeeCheckingStatus;
import com.example.employee.dto.EmployeeDto;
import com.example.employee.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class)
public class EmployeeControllerTest {

    private static final boolean JSONASSERT_STRICTLY_COMPARE = true;
    private static final String DUMMY_VALUE = "DUMMY";

    private ObjectMapper objMper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService mockEmployeeService;

    @Test
    public void checkExist_Exist_ShouldReturnExist() throws Exception {
        CheckEmployeeResult chkEmplRslt = CheckEmployeeResult.builder()
                .status(EmployeeCheckingStatus.ALREADY_EXIST)
                .mobile(DUMMY_VALUE)
                .build();
        EmployeeDto empl = EmployeeDto.builder()
                .mobile(DUMMY_VALUE)
                .gender(true)
                .email(DUMMY_VALUE)
                .firstName(DUMMY_VALUE)
                .lastName(DUMMY_VALUE)
                .build();
        Mockito.when(mockEmployeeService.checkExist(any())).thenReturn(chkEmplRslt);
        String jsonRqst = objMper.writeValueAsString(empl);
        String jsonRpsn = objMper.writeValueAsString(chkEmplRslt);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/emp/exist")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonRqst);

        MvcResult result =  mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals("HTTP response code not matched.", HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(jsonRpsn, response.getContentAsString(), JSONASSERT_STRICTLY_COMPARE);
    }

    @Test
    public void checkExist_NotExist_ShouldReturnCreated() throws Exception {
        CheckEmployeeResult chkEmplRslt = CheckEmployeeResult.builder()
                .status(EmployeeCheckingStatus.CREATED)
                .mobile(DUMMY_VALUE)
                .build();
        EmployeeDto empl = EmployeeDto.builder()
                .mobile(DUMMY_VALUE)
                .gender(true)
                .email(DUMMY_VALUE)
                .firstName(DUMMY_VALUE)
                .lastName(DUMMY_VALUE)
                .build();
        Mockito.when(mockEmployeeService.checkExist(any())).thenReturn(chkEmplRslt);
        String jsonRqst = objMper.writeValueAsString(empl);
        String jsonRpsn = objMper.writeValueAsString(chkEmplRslt);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/emp/exist")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonRqst);

        MvcResult result =  mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals("HTTP response code not matched.", HttpStatus.CREATED.value(), response.getStatus());
        JSONAssert.assertEquals(jsonRpsn, response.getContentAsString(), JSONASSERT_STRICTLY_COMPARE);
    }
}