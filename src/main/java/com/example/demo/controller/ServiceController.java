package com.example.demo.controller;

import com.example.demo.DTO.RequestDTO;
import com.example.demo.DTO.ResponseDTO;
import com.example.demo.service.Service1;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("${service.route.path}")
@Api(value = "MicroService1")
public class ServiceController {

    private static final Logger LOG = Logger.getLogger(ServiceController.class.getName());

    @GetMapping
    @ApiOperation(value = "Gets up string")
    public String getStatus() {
        return "up";
    }

    @Autowired
    Service1 service1;


    @PostMapping("/name")
    @ApiOperation(value = "prints and concat names the name and surname")
    public ResponseDTO createName(@Valid @RequestBody RequestDTO requestDTO) {
        LOG.log(Level.INFO,"in controller1 : recieved input ");
        return service1.getFinalResponse(requestDTO.getName(), requestDTO.getSurname());
    }

}


