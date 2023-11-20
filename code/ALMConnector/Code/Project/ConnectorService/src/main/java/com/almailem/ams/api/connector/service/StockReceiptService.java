package com.almailem.ams.api.connector.service;

import com.almailem.ams.api.connector.config.PropertiesConfig;
import com.almailem.ams.api.connector.controller.exception.BadRequestException;
import com.almailem.ams.api.connector.model.auth.AuthToken;
import com.almailem.ams.api.connector.model.stockreceipt.*;
import com.almailem.ams.api.connector.model.wms.InboundIntegrationLog;
import com.almailem.ams.api.connector.model.wms.Warehouse;
import com.almailem.ams.api.connector.model.wms.WarehouseApiResponse;
import com.almailem.ams.api.connector.repository.InboundIntegrationLogRepository;
import com.almailem.ams.api.connector.repository.StockReceiptHeaderRepository;
import com.almailem.ams.api.connector.repository.WarehouseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Slf4j
@Service
public class StockReceiptService {

    @Autowired
    StockReceiptHeaderRepository stockReceiptHeaderRepo;

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    PropertiesConfig propertiesConfig;

    @Autowired
    InboundIntegrationLogRepository inboundIntegrationLogRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    private String getTransactionServiceApiUrl() {
        return propertiesConfig.getTransactionServiceUrl();
    }

    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Object Convertor
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter
                .setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        return restTemplate;
    }

    /**
     * Get All StockReceipt Details
     *
     * @return
     */
    public List<StockReceiptHeader> getAllStockReceiptDetails() {
        List<StockReceiptHeader> stockReceipts = stockReceiptHeaderRepo.findAll();
        return stockReceipts;
    }

    public StockReceiptHeader updateProcessedInboundOrder(String receiptNumber) {
        StockReceiptHeader dbInboundOrder = stockReceiptHeaderRepo.findByReceiptNo(receiptNumber);
        log.info("orderId : " + receiptNumber);
        log.info("dbInboundOrder : " + dbInboundOrder);
        if (dbInboundOrder != null) {
            dbInboundOrder.setProcessedStatusId(10L);
            dbInboundOrder.setOrderProcessedOn(new Date());
            StockReceiptHeader inboundOrder = stockReceiptHeaderRepo.save(dbInboundOrder);
            return inboundOrder;
        }
        return dbInboundOrder;
    }

    /**
     *
     * @param stockReceipt
     * @return
     */
    public WarehouseApiResponse postStockReceipt(StockReceiptHeader stockReceipt) {
        AuthToken authToken = authTokenService.getTransactionServiceAuthToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "ClassicWMS RestTemplate");
        headers.add("Authorization", "Bearer " + authToken.getAccess_token());
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(getTransactionServiceApiUrl() + "warehouse/inbound/stockReceipt");
        HttpEntity<?> entity = new HttpEntity<>(stockReceipt, headers);
        ResponseEntity<WarehouseApiResponse> result =
                getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, WarehouseApiResponse.class);
        log.info("result : " + result.getStatusCode());
        return result.getBody();
    }

    /**
     *
     * @param inbound
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public InboundIntegrationLog createInboundIntegrationLog(StockReceiptHeader inbound) {
        Warehouse warehouse =
                getWarehouse(inbound.getCompanyCode(), inbound.getBranchCode());
        if (warehouse == null) {
            throw new BadRequestException("Warehouse Id not found ! ");
        }
        InboundIntegrationLog dbInboundIntegrationLog = new InboundIntegrationLog();
        dbInboundIntegrationLog.setLanguageId("EN");
        dbInboundIntegrationLog.setCompanyCodeId(warehouse.getCompanyCodeId());
        dbInboundIntegrationLog.setPlantId(warehouse.getPlantId());
        dbInboundIntegrationLog.setWarehouseId(warehouse.getWarehouseId());
        dbInboundIntegrationLog.setIntegrationLogNumber(String.valueOf(System.currentTimeMillis()));
        dbInboundIntegrationLog.setRefDocNumber(inbound.getReceiptNo());

//        Date date = null;
//        try {
//            if (inbound.getStockReceiptLine().get(0).getReceiptDate() != null) {
//                date = DateUtils.convertStringToDateByYYYYMMDD(inbound.getStockReceiptLine().get(0).getReceiptDate());
//            } else {
//                date = new Date();
//            }
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
        dbInboundIntegrationLog.setOrderReceiptDate(inbound.getStockReceiptLines().get(0).getReceiptDate());
        dbInboundIntegrationLog.setIntegrationStatus("FAILED");
        dbInboundIntegrationLog.setDeletionIndicator(0L);
        dbInboundIntegrationLog.setCreatedBy("MW_SR");
        dbInboundIntegrationLog.setCreatedOn(new Date());
        dbInboundIntegrationLog = inboundIntegrationLogRepository.save(dbInboundIntegrationLog);
        log.info("dbInboundIntegrationLog : " + dbInboundIntegrationLog);
        return dbInboundIntegrationLog;
    }

    /**
     *
     * @param companyCode
     * @param branchCode
     * @return
     */
    private Warehouse getWarehouse(String companyCode, String branchCode) {
        Optional<Warehouse> optWarehouse =
                warehouseRepository.findByCompanyCodeIdAndPlantIdAndLanguageIdAndDeletionIndicator(
                        companyCode, branchCode, "EN", 0L);
        if (optWarehouse.isEmpty()) {
            log.info("dbWarehouse not found for companyCode & branchCode: " + companyCode + "," + branchCode);
            return null;
        }

        return optWarehouse.get();
    }
}
