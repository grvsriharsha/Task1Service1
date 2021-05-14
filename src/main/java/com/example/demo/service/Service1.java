package com.example.demo.service;

import com.example.demo.DTO.RequestDTO;
import com.example.demo.DTO.ResponseDTO;
import com.example.demo.LogMethodParam;
import com.example.demo.exception.CustomException;
import com.example.demo.controller.ServiceController;
import com.example.demo.exception.ServiceUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Service1 {
    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOG = Logger.getLogger(ServiceController.class.getName());

    @LogMethodParam
    public ResponseDTO getFinalResponse(String name,String surname) {

        LOG.log(Level.FINE, "all responses");
        ResponseDTO res2 = null;
        ResponseDTO res3 = null;
        ResponseDTO finalResult = new ResponseDTO();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<?> entity1 = new HttpEntity<>(headers);

            res2 = restTemplate.exchange("http://localhost:8082/api/s2/v1", HttpMethod.GET, entity1, ResponseDTO.class).getBody();


            headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            RequestDTO requestDTO = new RequestDTO();
            requestDTO.setName(name);
            requestDTO.setSurname(surname);
            HttpEntity<?> entity2 = new HttpEntity<>(requestDTO, headers);

            res3 = restTemplate.exchange(
                    "http://localhost:8083/api/s3/v1/name", HttpMethod.POST, entity2, ResponseDTO.class).getBody();

            finalResult.setResponse(res2.getResponse() + " " + res3.getResponse());


        } catch (Exception e) {

            if (e instanceof ResourceAccessException)
                throw new ServiceUnavailableException(e.getMessage());

            //Wrap up underlying mssg and its status in customexception
            if (e instanceof HttpClientErrorException) {
                HttpClientErrorException httpClientErrorException = (HttpClientErrorException) e;

                throw new CustomException(httpClientErrorException.getResponseBodyAsString(), httpClientErrorException, httpClientErrorException.getStatusCode());
            }


        }
        return finalResult;
    }


}
