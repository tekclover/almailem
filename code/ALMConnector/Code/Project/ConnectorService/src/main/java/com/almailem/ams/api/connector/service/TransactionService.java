package com.almailem.ams.api.connector.service;


import com.almailem.ams.api.connector.model.picklist.PickListHeader;
import com.almailem.ams.api.connector.model.picklist.PickListLine;
import com.almailem.ams.api.connector.model.purchasereturn.PurchaseReturnHeader;
import com.almailem.ams.api.connector.model.purchasereturn.PurchaseReturnLine;
import com.almailem.ams.api.connector.model.salesreturn.SalesReturnHeader;
import com.almailem.ams.api.connector.model.salesinvoice.SalesInvoice;
import com.almailem.ams.api.connector.model.salesreturn.SalesReturnLine;
import com.almailem.ams.api.connector.model.stockreceipt.StockReceiptHeader;
import com.almailem.ams.api.connector.model.stockreceipt.StockReceiptLine;
import com.almailem.ams.api.connector.model.supplierinvoice.SupplierInvoiceHeader;
import com.almailem.ams.api.connector.model.supplierinvoice.SupplierInvoiceLine;
import com.almailem.ams.api.connector.model.transferin.TransferInHeader;
import com.almailem.ams.api.connector.model.transferin.TransferInLine;
import com.almailem.ams.api.connector.model.transferout.TransferOutHeader;
import com.almailem.ams.api.connector.model.transferout.TransferOutLine;
import com.almailem.ams.api.connector.model.wms.*;
import com.almailem.ams.api.connector.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    SupplierInvoiceService supplierInvoiceService;

    @Autowired
    StockReceiptService stockReceiptService;

    @Autowired
    SalesReturnService salesReturnService;

    @Autowired
    B2BTransferInService b2BTransferInService;

    @Autowired
    InterWarehouseTransferInService interWarehouseTransferInService;

    @Autowired
    ReturnPOService returnPOService;

    @Autowired
    InterWarehouseTransferOutService interWarehouseTransferOutService;

    @Autowired
    ShipmentOrderService shipmentOrderService;

    @Autowired
    SalesInvoiceService salesInvoiceService;

    @Autowired
    SalesOrderService salesOrderService;

    //-------------------------------------------------------------------------------------------

    @Autowired
    SupplierInvoiceHeaderRepository supplierInvoiceHeaderRepository;

    @Autowired
    StockReceiptHeaderRepository stockReceiptHeaderRepository;

    @Autowired
    SalesReturnHeaderRepository salesReturnHeaderRepository;

    @Autowired
    TransferInHeaderRepository transferInHeaderRepository;

    @Autowired
    PurchaseReturnHeaderRepository purchaseReturnHeaderRepository;

    @Autowired
    TransferOutHeaderRepository transferOutHeaderRepository;

    @Autowired
    PickListHeaderRepository pickListHeaderRepository;

    @Autowired
    SalesInvoiceRepository salesInvoiceRepository;

    //-------------------------------------------------------------------------------------------
    List<ASN> inboundList = null;
    List<com.almailem.ams.api.connector.model.wms.StockReceiptHeader> inboundSRList = null;
    List<SaleOrderReturn> inboundSRTList = null;
    List<B2bTransferIn> inboundB2BList = null;
    List<InterWarehouseTransferIn> inboundIWTList = null;
    List<ReturnPO> outboundRPOList = null;
    List<InterWarehouseTransferOut> outboundIWhtList = null;
    List<ShipmentOrder> outboundSOList = null;

    List<SalesOrder> outboundSalesOrderList = null;
    List<com.almailem.ams.api.connector.model.wms.SalesInvoice> outboundSIList = null;
    //=================================================================================================================
    static CopyOnWriteArrayList<ASN> spList = null;                               // ASN Inbound List
    static CopyOnWriteArrayList<com.almailem.ams.api.connector.model.wms.StockReceiptHeader> spSRList = null;                // StockReceipt Inbound List
    static CopyOnWriteArrayList<SaleOrderReturn> spSRTList = null;                // SaleOrder Inbound List
    static CopyOnWriteArrayList<B2bTransferIn> spB2BList = null;                    // B2B Inbound List
    static CopyOnWriteArrayList<InterWarehouseTransferIn> spIWTList = null;       // InterWarehouse Inbound List

    static CopyOnWriteArrayList<ReturnPO> spRPOList = null;                       // ReturnPO Outbound List
    static CopyOnWriteArrayList<InterWarehouseTransferOut> spIWhtList = null;     // InterWarehouseTransfer Outbound List
    static CopyOnWriteArrayList<ShipmentOrder> spSOList = null;                   // ShipmentOrder Outbound List

    static CopyOnWriteArrayList<SalesOrder> spSalesOrderList = null;                               // ASN Inbound List
    static CopyOnWriteArrayList<com.almailem.ams.api.connector.model.wms.SalesInvoice> spSIList = null;

    //==========================================================================================================================
    public WarehouseApiResponse processInboundOrder() throws IllegalAccessException, InvocationTargetException {
        if (inboundList == null || inboundList.isEmpty()) {
            List<SupplierInvoiceHeader> supplierInvoiceHeaders = supplierInvoiceHeaderRepository.findTopByProcessedStatusIdOrderByOrderReceivedOn(0L);
            inboundList = new ArrayList<>();
            ASN asn = new ASN();
            for (SupplierInvoiceHeader dbIBOrder : supplierInvoiceHeaders) {

                ASNHeader asnHeader = new ASNHeader();

                asnHeader.setAsnNumber(dbIBOrder.getSupplierInvoiceNo());
                asnHeader.setCompanyCode(dbIBOrder.getCompanyCode());
                asnHeader.setBranchCode(dbIBOrder.getBranchCode());
                asnHeader.setMiddlewareId(dbIBOrder.getSupplierInvoiceHeaderId());
                asnHeader.setMiddlewareTable("IB_SUPPLIER_INVOICE");

                List<ASNLine> asnLineList = new ArrayList<>();
                for (SupplierInvoiceLine line : dbIBOrder.getSupplierInvoiceLines()) {
                    ASNLine asnLine = new ASNLine();

                    asnLine.setSku(line.getItemCode());
                    asnLine.setSkuDescription(line.getItemDescription());
                    asnLine.setManufacturerCode(line.getManufacturerCode());
                    asnLine.setManufacturerName(line.getManufacturerShortName());
                    asnLine.setSupplierPartNumber(line.getSupplierPartNo());
                    asnLine.setSupplierName(line.getSupplierName());
                    asnLine.setSupplierCode(line.getSupplierCode());
                    asnLine.setPackQty(line.getInvoiceQty());
                    asnLine.setUom(line.getUnitOfMeasure());
                    asnLine.setExpectedQty(line.getInvoiceQty());
                    asnLine.setExpectedDate(String.valueOf(line.getInvoiceDate()));
                    asnLine.setLineReference(line.getLineNoForEachItem());
                    asnLine.setContainerNumber(line.getContainerNo());
                    asnLine.setManufacturerFullName(line.getManufacturerFullName());
                    asnLine.setPurchaseOrderNumber(line.getPurchaseOrderNo());
                    asnLine.setMiddlewareId(line.getSupplierInvoiceLineId());
                    asnLine.setMiddlewareHeaderId(dbIBOrder.getSupplierInvoiceHeaderId());
                    asnLine.setMiddlewareTable("IB_SUPPLIER_INVOICE");

                    asnLineList.add(asnLine);
                }
                asn.setAsnHeader(asnHeader);
                asn.setAsnLine(asnLineList);
                inboundList.add(asn);
            }
            spList = new CopyOnWriteArrayList<ASN>(inboundList);
            log.info("There is no Supplier Invoice record found to process (sql) ...Waiting..");
        }

        if (inboundList != null) {
            log.info("Latest Supplier Invoice found: " + inboundList);
            for (ASN inbound : spList) {
                try {
                    log.info("Supplier Invoice Number : " + inbound.getAsnHeader().getAsnNumber());
                    WarehouseApiResponse inboundHeader = supplierInvoiceService.postASNV2(inbound);
                    if (inboundHeader != null) {
                        // Updating the Processed Status
                        supplierInvoiceService.updateProcessedInboundOrder(inbound.getAsnHeader().getAsnNumber());
                        inboundList.remove(inbound);
                        return inboundHeader;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Error on inbound processing : " + e.toString());
                    // Updating the Processed Status
                    supplierInvoiceService.updateProcessedInboundOrder(inbound.getAsnHeader().getAsnNumber());
//                    supplierInvoiceService.createInboundIntegrationLog(inbound);
                    inboundList.remove(inbound);
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    //=====================================================StockReceipt============================================================
    public WarehouseApiResponse processInboundOrderSR() throws IllegalAccessException, InvocationTargetException {
        if (inboundSRList == null || inboundSRList.isEmpty()) {
            List<StockReceiptHeader> stockReceiptHeaders = stockReceiptHeaderRepository.findTopByProcessedStatusIdOrderByOrderReceivedOn(0L);
            inboundSRList = new ArrayList<>();
            for (StockReceiptHeader dbIBOrder : stockReceiptHeaders) {

                com.almailem.ams.api.connector.model.wms.StockReceiptHeader stockReceiptHeader = new com.almailem.ams.api.connector.model.wms.StockReceiptHeader();

                stockReceiptHeader.setCompanyCode(dbIBOrder.getCompanyCode());
                stockReceiptHeader.setBranchCode(dbIBOrder.getBranchCode());
                stockReceiptHeader.setReceiptNo(dbIBOrder.getReceiptNo());
                stockReceiptHeader.setMiddlewareId(dbIBOrder.getStockReceiptHeaderId());
                stockReceiptHeader.setMiddlewareTable("IB_STOCK_RECEIPT");

                List<com.almailem.ams.api.connector.model.wms.StockReceiptLine> stockReceiptLineList = new ArrayList<>();
                for (StockReceiptLine line : dbIBOrder.getStockReceiptLines()) {
                    com.almailem.ams.api.connector.model.wms.StockReceiptLine stockReceiptLine = new com.almailem.ams.api.connector.model.wms.StockReceiptLine();

                    stockReceiptLine.setItemCode(line.getItemCode());
                    stockReceiptLine.setItemDescription(line.getItemDescription());
                    stockReceiptLine.setManufacturerCode(line.getManufacturerCode());
                    stockReceiptLine.setManufacturerShortName(line.getManufacturerShortName());
                    stockReceiptLine.setSupplierPartNo(line.getSupplierPartNo());
                    stockReceiptLine.setSupplierName(line.getSupplierName());
                    stockReceiptLine.setSupplierCode(line.getSupplierCode());
                    stockReceiptLine.setUnitOfMeasure(line.getUnitOfMeasure());
                    stockReceiptLine.setReceiptQty(line.getReceiptQty());
                    stockReceiptLine.setReceiptDate(line.getReceiptDate());
                    stockReceiptLine.setLineNoForEachItem(line.getLineNoForEachItem());
                    stockReceiptLine.setManufacturerFullName(line.getManufacturerFullName());
                    stockReceiptLine.setMiddlewareId(line.getStockReceiptLineId());
                    stockReceiptLine.setMiddlewareHeaderId(dbIBOrder.getStockReceiptHeaderId());
                    stockReceiptLine.setMiddlewareTable("IB_STOCK_RECEIPT");

                    stockReceiptLineList.add(stockReceiptLine);
                }
                stockReceiptHeader.setStockReceiptLines(stockReceiptLineList);
                inboundSRList.add(stockReceiptHeader);
            }
            spSRList = new CopyOnWriteArrayList<com.almailem.ams.api.connector.model.wms.StockReceiptHeader>(inboundSRList);
            log.info("There is no Stock Receipt record found to process (sql) ...Waiting..");
        }

        if (inboundSRList != null) {
            log.info("Latest Stock Receipt found: " + inboundSRList);
            for (com.almailem.ams.api.connector.model.wms.StockReceiptHeader inbound : spSRList) {
                try {
                    log.info("Stock Receipt Number : " + inbound.getReceiptNo());
                    WarehouseApiResponse inboundHeader = stockReceiptService.postStockReceipt(inbound);
                    if (inboundHeader != null) {
                        // Updating the Processed Status
                        stockReceiptService.updateProcessedInboundOrder(inbound.getReceiptNo());
                        inboundSRList.remove(inbound);
                        return inboundHeader;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Error on inbound processing : " + e.toString());
                    // Updating the Processed Status
                    stockReceiptService.updateProcessedInboundOrder(inbound.getReceiptNo());
//                    stockReceiptService.createInboundIntegrationLog(inbound);
                    inboundSRList.remove(inbound);
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    //=====================================================SalesReturn============================================================
    public WarehouseApiResponse processInboundOrderSRT() throws IllegalAccessException, InvocationTargetException {
        if (inboundSRTList == null || inboundSRTList.isEmpty()) {
            List<SalesReturnHeader> salesReturnHeaders = salesReturnHeaderRepository.findTopByProcessedStatusIdOrderByOrderReceivedOn(0L);
            inboundSRTList = new ArrayList<>();
            for (SalesReturnHeader dbIBOrder : salesReturnHeaders) {
                SaleOrderReturn saleOrderReturn = new SaleOrderReturn();
                SOReturnHeader salesReturnHeader = new SOReturnHeader();

                salesReturnHeader.setCompanyCode(dbIBOrder.getCompanyCode());
                salesReturnHeader.setBranchCode(dbIBOrder.getBranchCodeOfReceivingWarehouse());
                salesReturnHeader.setTransferOrderNumber(dbIBOrder.getReturnOrderNo());
                salesReturnHeader.setMiddlewareId(dbIBOrder.getSalesReturnHeaderId());
                salesReturnHeader.setMiddlewareTable("IB_SALE_RETURN");

                List<SOReturnLine> salesReturnLineList = new ArrayList<>();
                for (SalesReturnLine line : dbIBOrder.getSalesReturnLines()) {
                    SOReturnLine salesReturnLine = new SOReturnLine();

                    salesReturnLine.setLineReference(line.getLineNoOfEachItem());
                    salesReturnLine.setSku(line.getItemCode());
                    salesReturnLine.setSkuDescription(line.getItemDescription());
                    salesReturnLine.setInvoiceNumber(line.getReferenceInvoiceNo());
                    salesReturnLine.setStoreID(line.getSourceBranchCode());
                    salesReturnLine.setSupplierPartNumber(line.getSupplierPartNo());
                    salesReturnLine.setManufacturerName(line.getManufacturerShortName());
                    salesReturnLine.setExpectedDate(line.getReturnOrderDate());
                    salesReturnLine.setExpectedQty(line.getReturnQty());
                    salesReturnLine.setUom(line.getUnitOfMeasure());
                    if (line.getNoOfPacks() != null) {
                        salesReturnLine.setPackQty(Double.valueOf(line.getNoOfPacks()));
                    }
                    salesReturnLine.setOrigin(line.getCountryOfOrigin());
                    salesReturnLine.setManufacturerCode(line.getManufacturerCode());
                    salesReturnLine.setManufacturerFullName(line.getManufacturerFullName());
                    salesReturnLine.setMiddlewareId(line.getSalesReturnLineId());
                    salesReturnLine.setMiddlewareHeaderId(dbIBOrder.getSalesReturnHeaderId());
                    salesReturnLine.setMiddlewareTable("IB_SALE_RETURN");

                    salesReturnLineList.add(salesReturnLine);
                }
                saleOrderReturn.setSoReturnHeader(salesReturnHeader);
                saleOrderReturn.setSoReturnLine(salesReturnLineList);
                inboundSRTList.add(saleOrderReturn);
            }
            spSRTList = new CopyOnWriteArrayList<SaleOrderReturn>(inboundSRTList);
            log.info("There is no Sales Return record found to process (sql) ...Waiting..");
        }

        if (inboundSRTList != null) {
            log.info("Latest Sales Return found: " + inboundSRTList);
            for (SaleOrderReturn inbound : spSRTList) {
                try {
                    log.info("Sales Return Number : " + inbound.getSoReturnHeader().getTransferOrderNumber());
                    WarehouseApiResponse inboundHeader = salesReturnService.postStockReceipt(inbound);
                    if (inboundHeader != null) {
                        // Updating the Processed Status
                        salesReturnService.updateProcessedInboundOrder(inbound.getSoReturnHeader().getTransferOrderNumber());
                        inboundSRTList.remove(inbound);
                        return inboundHeader;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Error on inbound processing : " + e.toString());
                    // Updating the Processed Status
                    salesReturnService.updateProcessedInboundOrder(inbound.getSoReturnHeader().getTransferOrderNumber());
//                    salesReturnService.createInboundIntegrationLog(inbound);
                    inboundSRTList.remove(inbound);
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    //=====================================================Interwarehouse============================================================
    public WarehouseApiResponse processInboundOrderIWT() throws IllegalAccessException, InvocationTargetException {

        if (inboundIWTList == null || inboundB2BList == null || inboundB2BList.isEmpty() || inboundIWTList.isEmpty()) {

            List<TransferInHeader> transferInHeaders = transferInHeaderRepository.findTopByProcessedStatusIdOrderByOrderReceivedOn(0L);

            inboundIWTList = new ArrayList<>();
            inboundB2BList = new ArrayList<>();
            String[] branchcode = new String[]{"115", "125", "212", "222"};

            for (TransferInHeader dbIBOrder : transferInHeaders) {

                boolean sourceBranchExist = Arrays.stream(branchcode).anyMatch(n -> n.equalsIgnoreCase(dbIBOrder.getSourceBranchCode()));
                boolean targetBranchExist = Arrays.stream(branchcode).anyMatch(n -> n.equalsIgnoreCase(dbIBOrder.getTargetBranchCode()));

                B2bTransferIn b2bTransferIn = new B2bTransferIn();
                List<B2bTransferInLine> b2bTransferInLines = new ArrayList<>();

                InterWarehouseTransferIn interWarehouseTransferIn = new InterWarehouseTransferIn();
                List<InterWarehouseTransferInLine> interWarehouseTransferInLineList = new ArrayList<>();

                if (!sourceBranchExist && targetBranchExist) {

                    B2bTransferInHeader b2bTransferInHeader = new B2bTransferInHeader();

                    b2bTransferInHeader.setCompanyCode(dbIBOrder.getTargetCompanyCode());
                    b2bTransferInHeader.setBranchCode(dbIBOrder.getTargetBranchCode());
                    b2bTransferInHeader.setTransferOrderNumber(dbIBOrder.getTransferOrderNo());
                    b2bTransferInHeader.setMiddlewareId(dbIBOrder.getTransferInHeaderId());
                    b2bTransferInHeader.setMiddlewareTable("IB_B2B");

                    for (TransferInLine line : dbIBOrder.getTransferInLines()) {

                        B2bTransferInLine b2bTransferInLine = new B2bTransferInLine();

                        b2bTransferInLine.setLineReference(line.getLineNoOfEachItem());
                        b2bTransferInLine.setSku(line.getItemCode());
                        b2bTransferInLine.setSkuDescription(line.getItemDescription());
                        b2bTransferInLine.setManufacturerName(line.getManufacturerShortName());
                        b2bTransferInLine.setExpectedQty(line.getTransferQty());
                        b2bTransferInLine.setUom(line.getUnitOfMeasure());
                        b2bTransferInLine.setManufacturerCode(line.getManufacturerCode());
                        b2bTransferInLine.setManufacturerFullName(line.getManufacturerFullName());
                        b2bTransferInLine.setExpectedDate(String.valueOf(dbIBOrder.getTransferOrderDate()));
                        b2bTransferInLine.setStoreID(dbIBOrder.getTargetBranchCode());
                        b2bTransferInLine.setOrigin(dbIBOrder.getSourceCompanyCode());
                        b2bTransferInLine.setBrand(line.getManufacturerShortName());
                        if (line.getTransferQty() != null) {
                            Double newDouble = new Double(line.getTransferQty());
                            Long tfrQty = newDouble.longValue(); ;
                            b2bTransferInLine.setPackQty(tfrQty);
                        }
                        b2bTransferInLine.setMiddlewareId(line.getTransferInLineId());
                        b2bTransferInLine.setMiddlewareHeaderId(dbIBOrder.getTransferInHeaderId());
                        b2bTransferInLine.setMiddlewareTable("IB_B2B");

                        b2bTransferInLines.add(b2bTransferInLine);
                    }

                    b2bTransferIn.setB2bTransferInHeader(b2bTransferInHeader);
                    b2bTransferIn.setB2bTransferLine(b2bTransferInLines);
                    inboundB2BList.add(b2bTransferIn);
                }

                if (sourceBranchExist && targetBranchExist) {

                    InterWarehouseTransferInHeader interWarehouseTransferInHeader = new InterWarehouseTransferInHeader();

                    interWarehouseTransferInHeader.setToCompanyCode(dbIBOrder.getTargetCompanyCode());
                    interWarehouseTransferInHeader.setToBranchCode(dbIBOrder.getTargetBranchCode());
                    interWarehouseTransferInHeader.setTransferOrderNumber(dbIBOrder.getTransferOrderNo());
                    interWarehouseTransferInHeader.setMiddlewareId(dbIBOrder.getTransferInHeaderId());
                    interWarehouseTransferInHeader.setMiddlewareTable("IB_IWT");

                    for (TransferInLine line : dbIBOrder.getTransferInLines()) {

                        InterWarehouseTransferInLine interWarehouseTransferInLine = new InterWarehouseTransferInLine();

                        interWarehouseTransferInLine.setLineReference(line.getLineNoOfEachItem());
                        interWarehouseTransferInLine.setSku(line.getItemCode());
                        interWarehouseTransferInLine.setSkuDescription(line.getItemDescription());
                        interWarehouseTransferInLine.setManufacturerName(line.getManufacturerShortName());
                        interWarehouseTransferInLine.setExpectedQty(line.getTransferQty());
                        interWarehouseTransferInLine.setPackQty(line.getTransferQty());
                        interWarehouseTransferInLine.setUom(line.getUnitOfMeasure());
                        interWarehouseTransferInLine.setManufacturerCode(line.getManufacturerCode());
                        interWarehouseTransferInLine.setManufacturerFullName(line.getManufacturerFullName());
                        interWarehouseTransferInLine.setExpectedDate(String.valueOf(dbIBOrder.getTransferOrderDate()));
                        interWarehouseTransferInLine.setFromBranchCode(dbIBOrder.getSourceBranchCode());
                        interWarehouseTransferInLine.setFromCompanyCode(dbIBOrder.getSourceCompanyCode());
                        interWarehouseTransferInLine.setBrand(line.getManufacturerShortName());
                        interWarehouseTransferInLine.setMiddlewareId(line.getTransferInLineId());
                        interWarehouseTransferInLine.setMiddlewareHeaderId(dbIBOrder.getTransferInHeaderId());
                        interWarehouseTransferInLine.setMiddlewareTable("IB_IWT");

                        interWarehouseTransferInLineList.add(interWarehouseTransferInLine);
                    }
                    interWarehouseTransferIn.setInterWarehouseTransferInHeader(interWarehouseTransferInHeader);
                    interWarehouseTransferIn.setInterWarehouseTransferInLine(interWarehouseTransferInLineList);
                    inboundIWTList.add(interWarehouseTransferIn);
                }
            }
            if (inboundB2BList != null) {
                spB2BList = new CopyOnWriteArrayList<B2bTransferIn>(inboundB2BList);
                log.info("There is no B2B record found to process (sql) ...Waiting..");
            }
            if (inboundIWTList != null) {
                spIWTList = new CopyOnWriteArrayList<InterWarehouseTransferIn>(inboundIWTList);
                log.info("There is no IWT record found to process (sql) ...Waiting..");
            }
        }

        if (inboundB2BList != null) {
            log.info("Latest B2B Transfer found: " + inboundB2BList);
            for (B2bTransferIn inbound : spB2BList) {
                try {
                    log.info("B2B Transfer Order Number : " + inbound.getB2bTransferInHeader().getTransferOrderNumber());
                    WarehouseApiResponse inboundHeader = b2BTransferInService.postB2BTransferIn(inbound);
                    if (inboundHeader != null) {
                        // Updating the Processed Status
                        b2BTransferInService.updateProcessedInboundOrder(inbound.getB2bTransferInHeader().getTransferOrderNumber());
                        inboundB2BList.remove(inbound);
                        return inboundHeader;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Error on inbound processing : " + e.toString());
                    // Updating the Processed Status
                    b2BTransferInService.updateProcessedInboundOrder(inbound.getB2bTransferInHeader().getTransferOrderNumber());
//                    b2BTransferInService.createInboundIntegrationLog(inbound);
                    inboundB2BList.remove(inbound);
                    throw new RuntimeException(e);
                }
            }
        }

        if (inboundIWTList != null) {
            log.info("Latest InterWareHouse Transfer found: " + inboundIWTList);
            for (InterWarehouseTransferIn inbound : spIWTList) {
                try {
                    log.info("InterWarehouse Transfer Order Number : " + inbound.getInterWarehouseTransferInHeader().getTransferOrderNumber());
                    WarehouseApiResponse inboundHeader = interWarehouseTransferInService.postIWTTransferIn(inbound);
                    if (inboundHeader != null) {
                        // Updating the Processed Status
                        interWarehouseTransferInService.updateProcessedInboundOrder(inbound.getInterWarehouseTransferInHeader().getTransferOrderNumber());
                        inboundIWTList.remove(inbound);
                        return inboundHeader;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Error on inbound processing : " + e.toString());
                    // Updating the Processed Status
                    interWarehouseTransferInService.updateProcessedInboundOrder(inbound.getInterWarehouseTransferInHeader().getTransferOrderNumber());
//                    interWarehouseTransferInV2Service.createInboundIntegrationLog(inbound);
                    inboundIWTList.remove(inbound);
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    //===========================================Outbound==============================================================
    //===========================================Purchase_Return=======================================================
    public WarehouseApiResponse processOutboundOrderRPO() throws IllegalAccessException, InvocationTargetException {
        if (outboundRPOList == null || outboundRPOList.isEmpty()) {
            List<PurchaseReturnHeader> purchaseReturns = purchaseReturnHeaderRepository.findTopByProcessedStatusIdOrderByOrderReceivedOn(0L);
            outboundRPOList = new ArrayList<>();
            ReturnPO returnPO = new ReturnPO();
            for (PurchaseReturnHeader dbObOrder : purchaseReturns) {
                ReturnPOHeader returnPOHeader = new ReturnPOHeader();

                returnPOHeader.setCompanyCode(dbObOrder.getCompanyCode());
                returnPOHeader.setBranchCode(dbObOrder.getBranchCode());
                returnPOHeader.setPoNumber(dbObOrder.getReturnOrderNo());
                returnPOHeader.setStoreID(dbObOrder.getBranchCode());
                returnPOHeader.setRequiredDeliveryDate(String.valueOf(dbObOrder.getReturnOrderDate()));
                returnPOHeader.setMiddlewareId(dbObOrder.getPurchaseReturnHeaderId());
                returnPOHeader.setMiddlewareTable("OB_PURCHASE_RETURN_HEADER");

                List<ReturnPOLine> returnPOLineList = new ArrayList<>();
                for (PurchaseReturnLine line : dbObOrder.getPurchaseReturnLines()) {
                    ReturnPOLine returnPOLine = new ReturnPOLine();

                    returnPOLine.setLineReference(line.getLineNoOfEachItemCode());
                    returnPOLine.setSku(line.getItemCode());
                    returnPOLine.setSkuDescription(line.getItemDescription());
                    returnPOLine.setReturnQty(line.getReturnOrderQty());
                    returnPOLine.setExpectedQty(line.getReturnOrderQty());
                    returnPOLine.setUom(line.getUnitOfMeasure());
                    returnPOLine.setManufacturerCode(line.getManufacturerCode());
                    returnPOLine.setManufacturerName(line.getManufacturerShortName());
                    returnPOLine.setBrand(line.getManufacturerFullName());
                    returnPOLine.setSupplierInvoiceNo(line.getSupplierInvoiceNo());
                    returnPOLine.setMiddlewareId(line.getPurchaseReturnLineId());
                    returnPOLine.setMiddlewareHeaderId(dbObOrder.getPurchaseReturnHeaderId());
                    returnPOLine.setMiddlewareTable("OB_PURCHASE_RETURN_LINE");
                    returnPOLineList.add(returnPOLine);
                }
                returnPO.setReturnPOLine(returnPOLineList);
                returnPO.setReturnPOHeader(returnPOHeader);
                outboundRPOList.add(returnPO);
            }
            spRPOList = new CopyOnWriteArrayList<ReturnPO>(outboundRPOList);
            log.info("There is no Purchase Return record found to process (sql) ...Waiting..");
        }

        if (outboundRPOList != null) {
            log.info("Latest Purchase Return found: " + outboundRPOList);
            for (ReturnPO outbound : spRPOList) {
                try {
                    log.info("Purchase Return Number: " + outbound.getReturnPOHeader().getPoNumber());
                    WarehouseApiResponse response = returnPOService.postReturnPOV2(outbound);
                    if (response != null) {
                        //Updating the Processed Status
                        returnPOService.updateProcessedOutboundOrder(outbound.getReturnPOHeader().getPoNumber());
                        outboundRPOList.remove(outbound);
                        return response;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Error on outbound processing: " + e.toString());
                    //Updating the Processed Status
                    returnPOService.updateProcessedOutboundOrder(outbound.getReturnPOHeader().getPoNumber());
                    outboundRPOList.remove(outbound);
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    //------------------------------------InterWarehouse & ShipmentOrder---------------------------------------------
    public WarehouseApiResponse processOutboundOrderIWT() throws IllegalAccessException, InvocationTargetException {

        if (outboundIWhtList == null || outboundSOList == null || outboundSOList.isEmpty() || outboundIWhtList.isEmpty()) {

            List<TransferOutHeader> transferOuts = transferOutHeaderRepository.findTopByProcessedStatusIdOrderByOrderReceivedOn(0L);
            outboundIWhtList = new ArrayList<>();
            outboundSOList = new ArrayList<>();
            String[] branchcode = new String[]{"115", "125", "212", "222"};

            for (TransferOutHeader dbObOrder : transferOuts) {

                boolean sourceBranchExist = Arrays.stream(branchcode).anyMatch(n -> n.equalsIgnoreCase(dbObOrder.getSourceBranchCode()));
                boolean targetBranchExist = Arrays.stream(branchcode).anyMatch(n -> n.equalsIgnoreCase(dbObOrder.getTargetBranchCode()));

                ShipmentOrder shipmentOrder = new ShipmentOrder();
                List<SOLine> soV2List = new ArrayList<>();

                InterWarehouseTransferOut iWhTransferOut = new InterWarehouseTransferOut();
                List<InterWarehouseTransferOutLine> iWhtOutLineList = new ArrayList<>();

                if (sourceBranchExist && !targetBranchExist) {
                    SOHeader soHeader = new SOHeader();

                    soHeader.setCompanyCode(dbObOrder.getSourceCompanyCode());
                    soHeader.setBranchCode(dbObOrder.getSourceBranchCode());
                    soHeader.setTransferOrderNumber(dbObOrder.getTransferOrderNumber());
                    soHeader.setRequiredDeliveryDate(String.valueOf(dbObOrder.getTransferOrderDate()));
                    soHeader.setStoreID(dbObOrder.getSourceBranchCode());
                    soHeader.setTargetCompanyCode(dbObOrder.getTargetCompanyCode());
                    soHeader.setTargetBranchCode(dbObOrder.getTargetBranchCode());
                    soHeader.setMiddlewareId(dbObOrder.getTransferOutHeaderId());
                    soHeader.setMiddlewareTable("OB_SHIPMENT_ORDER_HEADER");

                    for (TransferOutLine line : dbObOrder.getTransferOutLines()) {
                        SOLine soLine = new SOLine();

                        soLine.setLineReference(line.getLineNumberOfEachItem());
                        soLine.setSku(line.getItemCode());
                        soLine.setSkuDescription(line.getItemDescription());
                        soLine.setOrderedQty(line.getTransferOrderQty());
                        soLine.setExpectedQty(line.getTransferOrderQty());
                        soLine.setUom(line.getUnitOfMeasure());
                        soLine.setManufacturerCode(line.getManufacturerCode());
                        soLine.setManufacturerName(line.getManufacturerShortName());
                        soLine.setFromCompanyCode(dbObOrder.getSourceCompanyCode());
                        soLine.setMiddlewareId(line.getTransferOutLineId());
                        soLine.setMiddlewareHeaderId(dbObOrder.getTransferOutHeaderId());
                        soLine.setMiddlewareTable("OB_SHIPMENT_ORDER_LINE");
                        soV2List.add(soLine);
                    }
                    shipmentOrder.setSoHeader(soHeader);
                    shipmentOrder.setSoLine(soV2List);
                    outboundSOList.add(shipmentOrder);
                }

                if (sourceBranchExist && targetBranchExist) {
                    InterWarehouseTransferOutHeader iWhtOutHeader = new InterWarehouseTransferOutHeader();

                    iWhtOutHeader.setFromCompanyCode(dbObOrder.getSourceCompanyCode());
                    iWhtOutHeader.setToCompanyCode(dbObOrder.getTargetCompanyCode());
                    iWhtOutHeader.setTransferOrderNumber(dbObOrder.getTransferOrderNumber());
                    iWhtOutHeader.setFromBranchCode(dbObOrder.getSourceBranchCode());
                    iWhtOutHeader.setToBranchCode(dbObOrder.getTargetBranchCode());
                    iWhtOutHeader.setRequiredDeliveryDate(String.valueOf(dbObOrder.getTransferOrderDate()));
                    iWhtOutHeader.setMiddlewareId(dbObOrder.getTransferOutHeaderId());
                    iWhtOutHeader.setMiddlewareTable("OB_IWHTRANSFER_OUT_HEADER");

                    for (TransferOutLine line : dbObOrder.getTransferOutLines()) {
                        InterWarehouseTransferOutLine iWhtOutLine = new InterWarehouseTransferOutLine();

                        iWhtOutLine.setLineReference(line.getLineNumberOfEachItem());
                        iWhtOutLine.setSku(line.getItemCode());
                        iWhtOutLine.setSkuDescription(line.getItemDescription());
                        iWhtOutLine.setOrderedQty(line.getTransferOrderQty());
                        iWhtOutLine.setUom(line.getUnitOfMeasure());
                        iWhtOutLine.setManufacturerCode(line.getManufacturerCode());
                        iWhtOutLine.setManufacturerName(line.getManufacturerShortName());
                        iWhtOutLine.setMiddlewareId(line.getTransferOutLineId());
                        iWhtOutLine.setMiddlewareHeaderId(line.getTransferOutHeaderId());
                        iWhtOutLine.setMiddlewareTable("OB_IWHTRANSFER_OUT_LINE");
                        iWhtOutLineList.add(iWhtOutLine);
                    }
                    iWhTransferOut.setInterWarehouseTransferOutHeader(iWhtOutHeader);
                    iWhTransferOut.setInterWarehouseTransferOutLine(iWhtOutLineList);
                    outboundIWhtList.add(iWhTransferOut);
                }
            }
            if (outboundSOList != null) {
                spSOList = new CopyOnWriteArrayList<ShipmentOrder>(outboundSOList);
                log.info("There is no IWhTOut record found to process (sql) ...Waiting..");
            }
            if (outboundIWhtList != null) {
                spIWhtList = new CopyOnWriteArrayList<InterWarehouseTransferOut>(outboundIWhtList);
                log.info("There is no SO record found to process (sql) ...Waiting..");
            }
        }

        if (outboundSOList != null) {
            log.info("Latest Shipment Order found: " + outboundSOList);
            for (ShipmentOrder outbound : spSOList) {
                try {
                    log.info("SO Transfer Order Number: " + outbound.getSoHeader().getTransferOrderNumber());
                    WarehouseApiResponse response = shipmentOrderService.postShipmentOrder(outbound);
                    if (response != null) {
                        // Updating the Processed Status
                        shipmentOrderService.updateProcessedOutboundOrder(outbound.getSoHeader().getTransferOrderNumber());
                        outboundSOList.remove(outbound);
                        return response;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Error on outbound processing : " + e.toString());
                    //Updating the Processed Status
                    shipmentOrderService.updateProcessedOutboundOrder(outbound.getSoHeader().getTransferOrderNumber());
                    outboundSOList.remove(outbound);
                    throw new RuntimeException(e);
                }
            }
        }

        if (outboundIWhtList != null) {
            log.info("Latest Transfer Out found: " + outboundIWhtList);
            for (InterWarehouseTransferOut outbound : spIWhtList) {
                try {
                    log.info("IWT Transfer Out Number: " + outbound.getInterWarehouseTransferOutHeader().getTransferOrderNumber());
                    WarehouseApiResponse response = interWarehouseTransferOutService.postIWhTransferOutV2(outbound);
                    if (response != null) {
                        //Updating the Processed Status
                        interWarehouseTransferOutService.updateProcessedOutboundOrder(outbound.getInterWarehouseTransferOutHeader().getTransferOrderNumber());
                        outboundIWhtList.remove(outbound);
                        return response;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Error on outbound processing : " + e.toString());
                    //Updating the Processed Status
                    interWarehouseTransferOutService.updateProcessedOutboundOrder(outbound.getInterWarehouseTransferOutHeader().getTransferOrderNumber());
                    outboundIWhtList.remove(outbound);
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    public WarehouseApiResponse processOutboundOrderPL() throws IllegalAccessException, InvocationTargetException {
        if (outboundSalesOrderList == null || outboundSalesOrderList.isEmpty()) {
            List<PickListHeader> pickListHeaders = pickListHeaderRepository.findTopByProcessedStatusIdOrderByOrderReceivedOn(0L);
            outboundSalesOrderList = new ArrayList<>();
            SalesOrder salesOrder = new SalesOrder();
            for (PickListHeader dbOBOrder : pickListHeaders) {

                SalesOrderHeader salesOrderHeader = new SalesOrderHeader();

                salesOrderHeader.setSalesOrderNumber(dbOBOrder.getSalesOrderNo());
                salesOrderHeader.setCompanyCode(dbOBOrder.getCompanyCode());
                salesOrderHeader.setBranchCode(dbOBOrder.getBranchCode());
                salesOrderHeader.setPickListNumber(dbOBOrder.getPickListNo());
                salesOrderHeader.setRequiredDeliveryDate(String.valueOf(dbOBOrder.getPickListdate()));
                salesOrderHeader.setStoreID(dbOBOrder.getBranchCode());
                salesOrderHeader.setStoreName(dbOBOrder.getBranchCode());
                salesOrderHeader.setStatus("ACTIVE");
                salesOrderHeader.setMiddlewareId(dbOBOrder.getPickListHeaderId());
                salesOrderHeader.setMiddlewareTable("OB_SalesOrder");

                List<SalesOrderLine> salesOrderLines = new ArrayList<>();
                for (PickListLine line : dbOBOrder.getPickListLines()) {
                    SalesOrderLine salesOrderLine = new SalesOrderLine();

                    salesOrderLine.setLineReference(line.getLineNumberOfEachItem());
                    salesOrderLine.setSku(line.getItemCode());
                    salesOrderLine.setSkuDescription(line.getItemDescription());
                    salesOrderLine.setManufacturerCode(line.getManufacturerCode());
                    salesOrderLine.setManufacturerName(line.getManufacturerShortName());
                    salesOrderLine.setManufacturerFullName(line.getManufacturerFullName());
                    salesOrderLine.setUom(line.getUnitOfMeasure());
                    salesOrderLine.setOrderedQty(line.getPickListQty());
                    salesOrderLine.setExpectedQty(line.getPickedQty());
                    salesOrderLine.setPackQty(line.getPickedQty());
                    salesOrderLine.setMiddlewareId(line.getPickListLineId());
                    salesOrderLine.setMiddlewareHeaderId(dbOBOrder.getPickListHeaderId());
                    salesOrderLine.setMiddlewareTable("OB_SalesOrder");

                    salesOrderLines.add(salesOrderLine);
                }
                salesOrder.setSalesOrderHeader(salesOrderHeader);
                salesOrder.setSalesOrderLine(salesOrderLines);
                outboundSalesOrderList.add(salesOrder);
            }
            spSalesOrderList = new CopyOnWriteArrayList<SalesOrder>(outboundSalesOrderList);
            log.info("There is no record found to process (sql) ...Waiting..");
        }

        if (outboundSalesOrderList != null) {
            log.info("Latest Supplier Invoice found: " + outboundSalesOrderList);
            for (SalesOrder outbound : spSalesOrderList) {
                try {
                    log.info("Supplier Invoice Number : " + outbound.getSalesOrderHeader().getSalesOrderNumber());
                    WarehouseApiResponse outboundHeader = salesOrderService.postSalesOrder(outbound);
                    if (outboundHeader != null) {
                        // Updating the Processed Status
                        salesOrderService.updateProcessedInboundOrder(outbound.getSalesOrderHeader().getSalesOrderNumber());
                        outboundSalesOrderList.remove(outbound);
                        return outboundHeader;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Error on outbound processing : " + e.toString());
                    // Updating the Processed Status
                    salesOrderService.updateProcessedInboundOrder(outbound.getSalesOrderHeader().getSalesOrderNumber());
//                    salesOrderV2Service.createInboundIntegrationLog(outbound);
                    outboundSalesOrderList.remove(outbound);
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
    public WarehouseApiResponse processOutboundOrderSI() throws IllegalAccessException, InvocationTargetException {
        if (outboundSIList == null || outboundSIList.isEmpty()) {
            List<SalesInvoice> salesInvoiceList = salesInvoiceRepository.findTopByProcessedStatusIdOrderByOrderReceivedOn(0L);
            outboundSIList = new ArrayList<>();
            for (SalesInvoice dbOBOrder : salesInvoiceList) {

                com.almailem.ams.api.connector.model.wms.SalesInvoice salesInvoice = new  com.almailem.ams.api.connector.model.wms.SalesInvoice();

                salesInvoice.setCompanyCode(dbOBOrder.getCompanyCode());
                salesInvoice.setBranchCode(dbOBOrder.getBranchCode());
                salesInvoice.setSalesOrderNumber(dbOBOrder.getSalesOrderNumber());
                salesInvoice.setSalesInvoiceNumber(dbOBOrder.getSalesInvoiceNumber());
                salesInvoice.setPickListNumber(dbOBOrder.getPickListNumber());
                salesInvoice.setInvoiceDate(String.valueOf(dbOBOrder.getInvoiceDate()));
                salesInvoice.setMiddlewareId(dbOBOrder.getSalesInvoiceId());
                salesInvoice.setMiddlewareTable("OBSalesInvoice");

                outboundSIList.add(salesInvoice);
            }
            spSIList = new CopyOnWriteArrayList<com.almailem.ams.api.connector.model.wms.SalesInvoice>(outboundSIList);
            log.info("There is no record found to process (sql) ...Waiting..");
        }

        if (outboundSIList != null) {
            log.info("Latest Sales Invoice found: " + outboundSOList);
            for (com.almailem.ams.api.connector.model.wms.SalesInvoice outbound : spSIList) {
                try {
                    log.info("Sales Invoice Number : " + outbound.getSalesInvoiceNumber());
                    WarehouseApiResponse outboundHeader = salesInvoiceService.postSalesInvoice(outbound);
                    if (outboundHeader != null) {
                        // Updating the Processed Status
                        salesInvoiceService.updateProcessedOutboundOrder(outbound.getSalesInvoiceNumber());
                        outboundSIList.remove(outbound);
                        return outboundHeader;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Error on outbound processing : " + e.toString());
                    // Updating the Processed Status
                    salesInvoiceService.updateProcessedOutboundOrder(outbound.getSalesInvoiceNumber());
//                      salesInvoiceService.createInboundIntegrationLog(outbound);
                    outboundSIList.remove(outbound);
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

}