package com.almailem.ams.api.connector.service;

import com.almailem.ams.api.connector.config.PropertiesConfig;
import com.almailem.ams.api.connector.controller.exception.BadRequestException;
import com.almailem.ams.api.connector.model.auth.AuthToken;
import com.almailem.ams.api.connector.model.supplierinvoice.*;
import com.almailem.ams.api.connector.model.wms.*;
import com.almailem.ams.api.connector.repository.SupplierInvoiceHeaderRepository;
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

@Service
@Slf4j
public class SupplierInvoiceService {

    @Autowired
    private SupplierInvoiceHeaderRepository supplierInvoiceHeaderRepository;

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    PropertiesConfig propertiesConfig;


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
     * GET ALL
     */
    public List<SupplierInvoiceHeader> getAllSupplierInvoiceHeader() {
        List<SupplierInvoiceHeader> supplierInvoiceHeader = supplierInvoiceHeaderRepository.findAll();
        return supplierInvoiceHeader;
    }

    /**
     *
     * @param asnNumber
     * @return
     */
    public SupplierInvoiceHeader updateProcessedInboundOrder(String asnNumber) {
        SupplierInvoiceHeader dbInboundOrder = supplierInvoiceHeaderRepository.findTopBySupplierInvoiceNoOrderByOrderReceivedOnDesc(asnNumber);
        log.info("orderId : " + asnNumber);
        log.info("dbInboundOrder : " + dbInboundOrder);
        if (dbInboundOrder != null) {
            dbInboundOrder.setProcessedStatusId(10L);
            dbInboundOrder.setOrderProcessedOn(new Date());
            SupplierInvoiceHeader inboundOrder = supplierInvoiceHeaderRepository.save(dbInboundOrder);
            return inboundOrder;
        }
        return dbInboundOrder;
    }

    /**
     * @param asnv2
     * @return
     */
    public WarehouseApiResponse postASNV2(ASNV2 asnv2) {
        AuthToken authToken = authTokenService.getTransactionServiceAuthToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "ClassicWMS RestTemplate");
        headers.add("Authorization", "Bearer " + authToken.getAccess_token());
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(getTransactionServiceApiUrl() + "warehouse/inbound/asn/v2");
        HttpEntity<?> entity = new HttpEntity<>(asnv2, headers);
        ResponseEntity<WarehouseApiResponse> result =
                getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, WarehouseApiResponse.class);
        log.info("result : " + result.getStatusCode());
        return result.getBody();
    }

}
