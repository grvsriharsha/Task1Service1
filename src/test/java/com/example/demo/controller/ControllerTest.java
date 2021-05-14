package com.example.demo.controller;

import com.example.demo.DTO.ResponseDTO;
import com.example.demo.exception.ServiceUnavailableException;
import com.example.demo.service.Service1;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {ServiceController.class})
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @Value("${service.route.path}")
    private String apiUrl;

    protected Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    @MockBean
    Service1 service;

    @Mock
    private RestTemplate restTemplate;

    @BeforeClass
    public static void setUp() {

    }

    @Test
    public void testGet() throws Exception {

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponse("Hello");

        MvcResult mvcResult = mvc.perform(get("http://localhost:8081/" + apiUrl)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().equalsIgnoreCase("up"));

    }

    @Test
    public void testCreateName() throws Exception {


        String requestString = "{\"Name\":\"sri\",\"Surname\":\"harsha\"}";

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponse("hello sri harsha");

        given(this.service.getFinalResponse(anyString(), anyString())).willReturn(responseDTO);

        mvc.perform(post("http://localhost:8081/" + apiUrl + "/name")
                .contentType(MediaType.APPLICATION_JSON).content(requestString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", is("hello sri harsha")));


    }

    @Test
    public void testWithWrongInput() throws Exception {

        String requestString = "{\"Name\":\"\",\"Surname\":\"harsha\"}";

//        ResponseDTO responseDTO = new ResponseDTO();
//        responseDTO.setResponse("sri harsha");

        // given(this.service.createName(anyString(), anyString())).willReturn(responseDTO);

        MvcResult mvcResult = mvc.perform(post("http://localhost:8081/" + apiUrl + "/name")
                .contentType(MediaType.APPLICATION_JSON).content(requestString))
                .andExpect(status().is4xxClientError()).andReturn();


        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException);
        assertTrue(mvcResult.getResolvedException().getMessage().contains("size should be between 1 and 50"));

    }

}