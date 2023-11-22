//package com.almailem.ams.api.connector.service;
//
//import com.almailem.ams.api.connector.config.PropertiesConfig;
//import com.almailem.ams.api.connector.controller.exception.BadRequestException;
//import com.almailem.ams.api.connector.model.auth.AuthToken;
//import com.almailem.ams.api.connector.model.transferin.TransferInHeader;
//import com.almailem.ams.api.connector.model.wms.*;
//import com.almailem.ams.api.connector.util.DateUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.lang.reflect.InvocationTargetException;
//import java.text.ParseException;
//import java.util.*;
//
//@Slf4j
//@Service
//public class B2BTransferInService {
//
//    @Autowired
//    B2BInHeaderRepository b2BInHeaderRepository;
//
//    @Autowired
//    private AuthTokenService authTokenService;
//
//    @Autowired
//    PropertiesConfig propertiesConfig;
//
//    private String getTransactionServiceApiUrl() {
//        return propertiesConfig.getTransactionServiceUrl();
//    }
//
//    public RestTemplate getRestTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Object Convertor
//        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//        mappingJackson2HttpMessageConverter
//                .setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
//        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
//        return restTemplate;
//    }
//
//    /**
//     * Get All B2BTransferIn Details
//     *
//     * @return
//     */
////    public List<TransferInHeader> getAllB2BTransferInDetails() {
////        List<TransferInHeader> headerRepoAll = b2BInHeaderRepository.findAll();
////        return headerRepoAll;
////    }
////
////    public B2BHeader updateProcessedInboundOrder(String asnNumber) {
////        B2BHeader dbInboundOrder = b2BInHeaderRepository.findByTransferOrderNo(asnNumber);
////        log.info("orderId : " + asnNumber);
////        log.info("dbInboundOrder : " + dbInboundOrder);
////        if (dbInboundOrder != null) {
////            dbInboundOrder.setProcessedStatusId(10L);
////            dbInboundOrder.setOrderProcessedOn(new Date());
////            B2BHeader inboundOrder = b2BInHeaderRepository.save(dbInboundOrder);
////            return inboundOrder;
////        }
////        return dbInboundOrder;
////    }
////
////    /**
////     *
////     * @param b2bTransferIn
////     * @return
////     */
////    public WarehouseApiResponse postB2BTransferIn(B2bTransferIn b2bTransferIn) {
////        AuthToken authToken = authTokenService.getTransactionServiceAuthToken();
////        HttpHeaders headers = new HttpHeaders();
////        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
////        headers.add("User-Agent", "ClassicWMS RestTemplate");
////        headers.add("Authorization", "Bearer " + authToken.getAccess_token());
////        UriComponentsBuilder builder =
////                UriComponentsBuilder.fromHttpUrl(getTransactionServiceApiUrl() + "warehouse/inbound/b2bTransferIn");
////        HttpEntity<?> entity = new HttpEntity<>(b2bTransferIn, headers);
////        ResponseEntity<WarehouseApiResponse> result =
////                getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, WarehouseApiResponse.class);
////        log.info("result : " + result.getStatusCode());
////        return result.getBody();
////    }
//
//}
