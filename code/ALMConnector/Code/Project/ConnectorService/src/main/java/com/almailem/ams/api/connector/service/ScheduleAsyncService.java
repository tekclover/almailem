package com.almailem.ams.api.connector.service;

import com.almailem.ams.api.connector.model.wms.WarehouseApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ScheduleAsyncService {

    @Autowired
    InboundService inboundService;

    //-------------------------------------------------------------------------------------------


    @Async("asyncTaskExecutor")
    public CompletableFuture<WarehouseApiResponse> scheduleSupplierInvoice() throws InterruptedException, InvocationTargetException, IllegalAccessException {

        log.info("supplierInvoice Started Processing");
        WarehouseApiResponse supplierInvoice = inboundService.processInboundOrder();
        log.info("supplierInvoice finished Processing");
        return CompletableFuture.completedFuture(supplierInvoice);

    }

    @Async("asyncTaskExecutor")
    public CompletableFuture<WarehouseApiResponse> scheduleStockReceipt() throws InterruptedException, InvocationTargetException, IllegalAccessException {

        log.info("stockReceipt Started Processing");
        WarehouseApiResponse stockReceipt = inboundService.processInboundOrderSR();
        log.info("stockReceipt finished Processing");
        return CompletableFuture.completedFuture(stockReceipt);

    }

    @Async("asyncTaskExecutor")
    public CompletableFuture<WarehouseApiResponse> scheduleSalesReturn() throws InterruptedException, InvocationTargetException, IllegalAccessException {

        log.info("salesReturn Started Processing");
        WarehouseApiResponse salesReturn = inboundService.processInboundOrderSRT();
        log.info("salesReturn finished Processing");
        return CompletableFuture.completedFuture(salesReturn);

    }
    @Async("asyncTaskExecutor")
    public CompletableFuture<WarehouseApiResponse> scheduleB2BTransfer() throws InterruptedException, InvocationTargetException, IllegalAccessException {

        log.info("B2B Transfer Started Processing");
        WarehouseApiResponse b2btransfer = inboundService.processInboundOrderB2B();
        log.info("B2B Transfer finished Processing");
        return CompletableFuture.completedFuture(b2btransfer);

    }

    @Async("asyncTaskExecutor")
    public CompletableFuture<WarehouseApiResponse> scheduleIWTTransfer() throws InterruptedException, InvocationTargetException, IllegalAccessException {

        log.info("IWT Transfer Started Processing");
        WarehouseApiResponse iwtTransfer = inboundService.processInboundOrderIWT();
        log.info("IWT Transfer finished Processing");
        return CompletableFuture.completedFuture(iwtTransfer);

    }
}
