package com.almailem.ams.api.connector.service;

import com.almailem.ams.api.connector.config.PropertiesConfig;
import com.almailem.ams.api.connector.controller.exception.BadRequestException;
import com.almailem.ams.api.connector.model.auth.AuthToken;
import com.almailem.ams.api.connector.model.b2b.B2BHeader;
import com.almailem.ams.api.connector.model.wms.*;
import com.almailem.ams.api.connector.repository.B2BInHeaderRepository;
import com.almailem.ams.api.connector.repository.InboundIntegrationLogRepository;
import com.almailem.ams.api.connector.repository.WarehouseRepository;
import com.almailem.ams.api.connector.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.*;

@Slf4j
@Service
public class B2BTransferInService {

    @Autowired
    B2BInHeaderRepository b2BInHeaderRepository;

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
     * Get All B2BTransferIn Details
     *
     * @return
     */
    public List<B2BHeader> getAllB2BTransferInDetails() {
        List<B2BHeader> headerRepoAll = b2BInHeaderRepository.findAll();
        return headerRepoAll;
    }

    public B2BHeader updateProcessedInboundOrder(String asnNumber) {
        B2BHeader dbInboundOrder = b2BInHeaderRepository.findByTransferOrderNo(asnNumber);
        log.info("orderId : " + asnNumber);
        log.info("dbInboundOrder : " + dbInboundOrder);
        if (dbInboundOrder != null) {
            dbInboundOrder.setProcessedStatusId(10L);
            dbInboundOrder.setOrderProcessedOn(new Date());
            B2BHeader inboundOrder = b2BInHeaderRepository.save(dbInboundOrder);
            return inboundOrder;
        }
        return dbInboundOrder;
    }

    /**
     *
     * @param b2bTransferIn
     * @return
     */
    public WarehouseApiResponse postB2BTransferIn(B2bTransferIn b2bTransferIn) {
        AuthToken authToken = authTokenService.getTransactionServiceAuthToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "ClassicWMS RestTemplate");
        headers.add("Authorization", "Bearer " + authToken.getAccess_token());
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(getTransactionServiceApiUrl() + "warehouse/inbound/b2bTransferIn");
        HttpEntity<?> entity = new HttpEntity<>(b2bTransferIn, headers);
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
    public InboundIntegrationLog createInboundIntegrationLog(B2bTransferIn inbound) {
        Warehouse warehouse =
                getWarehouse(inbound.getB2bTransferInHeader().getCompanyCode(), inbound.getB2bTransferInHeader().getBranchCode());
        if (warehouse == null) {
            throw new BadRequestException("Warehouse Id not found ! ");
        }
        InboundIntegrationLog dbInboundIntegrationLog = new InboundIntegrationLog();
        dbInboundIntegrationLog.setLanguageId("EN");
        dbInboundIntegrationLog.setCompanyCodeId(warehouse.getCompanyCodeId());
        dbInboundIntegrationLog.setPlantId(warehouse.getPlantId());
        dbInboundIntegrationLog.setWarehouseId(warehouse.getWarehouseId());
        dbInboundIntegrationLog.setIntegrationLogNumber(String.valueOf(System.currentTimeMillis()));
        dbInboundIntegrationLog.setRefDocNumber(inbound.getB2bTransferInHeader().getTransferOrderNumber());

        Date date = null;
        try {
            if (inbound.getB2bTransferLine().get(0).getExpectedDate() != null) {
                date = DateUtils.convertStringToDateByYYYYMMDD(inbound.getB2bTransferLine().get(0).getExpectedDate());
            } else {
                date = new Date();
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        dbInboundIntegrationLog.setOrderReceiptDate(date);
        dbInboundIntegrationLog.setIntegrationStatus("FAILED");
        dbInboundIntegrationLog.setDeletionIndicator(0L);
        dbInboundIntegrationLog.setCreatedBy("MW_B2B");
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
