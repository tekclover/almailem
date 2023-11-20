package com.almailem.ams.api.connector.controller;

import com.almailem.ams.api.connector.model.transferout.TransferOutHeader;
import com.almailem.ams.api.connector.service.InterWarehouseTransferOutV2Service;
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
@Api(tags = {"InterWarehouseTransferOutV2"}, value = "InterWarehouseTransferOutV2 Operations related to InterWarehouseTransferOutV2Controller")
@SwaggerDefinition(tags = {@Tag(name = "InterWarehouseTransferOutV2", description = "Operations related to InterWarehouseTransferOutV2")})
@RequestMapping("/interwarehousetransferoutv2")
@RestController
public class InterWarehouseTransferOutV2Controller {

    @Autowired
    InterWarehouseTransferOutV2Service iWhTransferOutV2Service;

    //Get All InterWarehouseTransferOutV2 Details
    @ApiOperation(response = TransferOutHeader.class, value = "Get All InterWarehouseTransferOutV2 Details")
    @GetMapping("")
    public ResponseEntity<?> getAllInterWhTransferOutV2s() {
        List<TransferOutHeader> transferOuts = iWhTransferOutV2Service.getAllInterWhTransferOutV2Details();
        return new ResponseEntity<>(transferOuts, HttpStatus.OK);
    }
}
