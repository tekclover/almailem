package com.almailem.ams.api.connector.controller;

import com.almailem.ams.api.connector.model.periodic.PeriodicHeader;
import com.almailem.ams.api.connector.service.PeriodicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@Api(tags = {"Periodic"}, value = "Periodic Operations related to PeriodicController")
@SwaggerDefinition(tags = {@Tag(name = "Periodic", description = "Operations related to Periodic")})
@RequestMapping("/periodic")
@RestController
public class PeriodicController {

    @Autowired
    PeriodicService periodicService;

    //Get All StockCount Periodic Details
    @ApiOperation(response = PeriodicHeader.class, value = "Get All Periodic Details")
    @GetMapping("")
    public ResponseEntity<?> getAllPeriodic() {
        List<PeriodicHeader> periodic = periodicService.getAllPeriodicDetails();
        return new ResponseEntity<>(periodic, HttpStatus.OK);
    }
}
