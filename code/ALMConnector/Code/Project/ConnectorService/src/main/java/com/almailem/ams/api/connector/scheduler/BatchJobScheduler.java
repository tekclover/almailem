package com.almailem.ams.api.connector.scheduler;

import com.almailem.ams.api.connector.model.wms.WarehouseApiResponse;
import com.almailem.ams.api.connector.service.ScheduleAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class BatchJobScheduler {

    @Autowired
    ScheduleAsyncService scheduleAsyncService;

    //-------------------------------------------------------------------------------------------

    @Scheduled(fixedDelay = 50000)
    public void scheduleJob() throws InterruptedException, InvocationTargetException, IllegalAccessException {

        CompletableFuture<WarehouseApiResponse> supplierInvoice = scheduleAsyncService.scheduleSupplierInvoice();
        CompletableFuture<WarehouseApiResponse> stockReceipt = scheduleAsyncService.scheduleStockReceipt();
        CompletableFuture<WarehouseApiResponse> salesReturn = scheduleAsyncService.scheduleSalesReturn();
        CompletableFuture<WarehouseApiResponse> b2bTransfer = scheduleAsyncService.scheduleB2BTransfer();
        CompletableFuture<WarehouseApiResponse> iwtTransfer = scheduleAsyncService.scheduleIWTTransfer();

//        CompletableFuture.allOf(supplierInvoice,stockReceipt,salesReturn,b2bTransfer,iwtTransfer).join();

    }

}