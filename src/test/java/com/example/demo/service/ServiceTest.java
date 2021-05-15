package com.example.demo.service;

import com.example.demo.DTO.ResponseDTO;
import com.example.demo.exception.ServiceUnavailableException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class ServiceTest {

    @InjectMocks
    private Service1 service;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetResponse() throws Exception {

        ResponseDTO res2 = new ResponseDTO();
        res2.setResponse("Hello");
        ResponseEntity responseEntity2 = new ResponseEntity(res2, HttpStatus.OK);

        ResponseDTO res3 = new ResponseDTO();
        res3.setResponse("sri harsha");
        ResponseEntity responseEntity3 = new ResponseEntity(res3, HttpStatus.OK);


        given(restTemplate.exchange(
                anyString(),
                Matchers.eq(HttpMethod.GET),
                any(),
                Matchers.eq(ResponseDTO.class))).
                willReturn(responseEntity2);


        given(restTemplate.exchange(
                anyString(),
                Matchers.eq(HttpMethod.POST),
                any(),
                Matchers.eq(ResponseDTO.class))).
                willReturn(responseEntity3);


        assertEquals(service.getFinalResponse("sri", "harsha").getResponse(), "Hello sri harsha");

    }

    @Test(expected = ServiceUnavailableException.class)
    public void testWhenService3IsDown() throws Exception {

        ResponseDTO res2 = new ResponseDTO();
        res2.setResponse("Hello");
        ResponseEntity responseEntity2 = new ResponseEntity(res2, HttpStatus.OK);

        ResponseDTO res3 = new ResponseDTO();
        res3.setResponse("sri harsha");
        ResponseEntity responseEntity3 = new ResponseEntity(res3, HttpStatus.SERVICE_UNAVAILABLE);


        given(restTemplate.exchange(
                anyString(),
                Matchers.eq(HttpMethod.GET),
                any(),
                Matchers.eq(ResponseDTO.class))).
                willReturn(responseEntity2);


        given(restTemplate.exchange(
                anyString(),
                Matchers.eq(HttpMethod.POST),
                any(),
                Matchers.eq(ResponseDTO.class))).
                willThrow(new ResourceAccessException("service3 is unavailable"));


        service.getFinalResponse("sri", "harsha").getResponse();


    }


}
