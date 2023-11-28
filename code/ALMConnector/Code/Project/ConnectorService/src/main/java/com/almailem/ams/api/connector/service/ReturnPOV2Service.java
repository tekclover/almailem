package com.almailem.ams.api.connector.service;

import com.almailem.ams.api.connector.config.PropertiesConfig;
import com.almailem.ams.api.connector.model.auth.AuthToken;
import com.almailem.ams.api.connector.model.purchasereturn.PurchaseReturnHeader;
import com.almailem.ams.api.connector.model.wms.ReturnPOV2;
import com.almailem.ams.api.connector.model.wms.WarehouseApiResponse;
import com.almailem.ams.api.connector.repository.PurchaseReturnHeaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ReturnPOV2Service {

    @Autowired
    PurchaseReturnHeaderRepository purchaseReturnHeaderRepository;

    @Autowired
    PropertiesConfig propertiesConfig;

    @Autowired
    AuthTokenService authTokenService;

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
     * Get All ReturnPOV2 Details
     *
     * @return
     */
    public List<PurchaseReturnHeader> getAllReturnPoV2Details() {
        List<PurchaseReturnHeader> purchaseReturns = purchaseReturnHeaderRepository.findAll();
        return purchaseReturns;
    }

    /**
     * @param returnOrderNo
     * @return
     */
    public void updateProcessedOutboundOrder(String returnOrderNo) {
        PurchaseReturnHeader dbObOrder = purchaseReturnHeaderRepository.findTopByReturnOrderNoOrderByOrderReceivedOnDesc(returnOrderNo);
        log.info("orderId: " + returnOrderNo);
        log.info("dbOutboundOrder: " + dbObOrder);
        if (dbObOrder != null) {
            dbObOrder.setProcessedStatusId(10L);
            dbObOrder.setOrderProcessedOn(new Date());
//            PurchaseReturnHeader obOrder = purchaseReturnHeaderRepository.save(dbObOrder);
            purchaseReturnHeaderRepository.updateProcessStatusId(returnOrderNo,new Date());
//            return null;
        }
//        return dbObOrder;
    }

    public WarehouseApiResponse postReturnPOV2(ReturnPOV2 returnPOV2) {
        AuthToken authToken = authTokenService.getTransactionServiceAuthToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "ClassicWMS RestTemplate");
        headers.add("Authorization", "Bearer " + authToken.getAccess_token());
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(getTransactionServiceApiUrl() + "warehouse/outbound/returnpov2");
        HttpEntity<?> entity = new HttpEntity<>(returnPOV2, headers);
        ResponseEntity<WarehouseApiResponse> result =
                getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, WarehouseApiResponse.class);
        log.info("result: " + result.getStatusCode());
        return result.getBody();
    }
}
