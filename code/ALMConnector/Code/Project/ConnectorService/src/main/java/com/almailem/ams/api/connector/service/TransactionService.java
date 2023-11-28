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
    InterWarehouseTransferInV2Service interWarehouseTransferInV2Service;

    @Autowired
    ReturnPOV2Service returnPOV2Service;

    @Autowired
    InterWarehouseTransferOutV2Service interWarehouseTransferOutService;

    @Autowired
    ShipmentOrderV2Service shipmentOrderService;

    @Autowired
    SalesInvoiceService salesInvoiceService;

    @Autowired
    SalesOrderV2Service salesOrderV2Service;

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
    List<ASNV2> inboundList = null;
    List<com.almailem.ams.api.connector.model.wms.StockReceiptHeader> inboundSRList = null;
    List<SaleOrderReturnV2> inboundSRTList = null;
    List<B2bTransferIn> inboundB2BList = null;
    List<InterWarehouseTransferInV2> inboundIWTList = null;
    List<ReturnPOV2> outboundRPOList = null;
    List<InterWarehouseTransferOutV2> outboundIWhtList = null;
    List<ShipmentOrderV2> outboundSOList = null;

    List<SalesOrderV2> outboundSalesOrderList = null;
    List<com.almailem.ams.api.connector.model.wms.SalesInvoice> outboundSIList = null;
    //=================================================================================================================
    static CopyOnWriteArrayList<ASNV2> spList = null;                               // ASN Inbound List
    static CopyOnWriteArrayList<com.almailem.ams.api.connector.model.wms.StockReceiptHeader> spSRList = null;                // StockReceipt Inbound List
    static CopyOnWriteArrayList<SaleOrderReturnV2> spSRTList = null;                // SaleOrder Inbound List
    static CopyOnWriteArrayList<B2bTransferIn> spB2BList = null;                    // B2B Inbound List
    static CopyOnWriteArrayList<InterWarehouseTransferInV2> spIWTList = null;       // InterWarehouse Inbound List

    static CopyOnWriteArrayList<ReturnPOV2> spRPOList = null;                       // ReturnPO Outbound List
    static CopyOnWriteArrayList<InterWarehouseTransferOutV2> spIWhtList = null;     // InterWarehouseTransfer Outbound List
    static CopyOnWriteArrayList<ShipmentOrderV2> spSOList = null;                   // ShipmentOrder Outbound List

    static CopyOnWriteArrayList<SalesOrderV2> spSalesOrderList = null;                               // ASN Inbound List
    static CopyOnWriteArrayList<com.almailem.ams.api.connector.model.wms.SalesInvoice> spSIList = null;

    //==========================================================================================================================
    public WarehouseApiResponse processInboundOrder() throws IllegalAccessException, InvocationTargetException {
        if (inboundList == null || inboundList.isEmpty()) {
            List<SupplierInvoiceHeader> supplierInvoiceHeaders = supplierInvoiceHeaderRepository.findTopByProcessedStatusIdOrderByOrderReceivedOn(0L);
            inboundList = new ArrayList<>();
            ASNV2 asn = new ASNV2();
            for (SupplierInvoiceHeader dbIBOrder : supplierInvoiceHeaders) {

                ASNHeaderV2 asnHeader = new ASNHeaderV2();

                asnHeader.setAsnNumber(dbIBOrder.getSupplierInvoiceNo());
                asnHeader.setCompanyCode(dbIBOrder.getCompanyCode());
                asnHeader.setBranchCode(dbIBOrder.getBranchCode());
//                asnHeader.setPurchaseOrderNumber(dbIBOrder.getPurchaseOrderNo());
                asnHeader.setMiddlewareId(dbIBOrder.getSupplierInvoiceHeaderId());
                asnHeader.setMiddlewareTable("IB_SUPPLIER_INVOICE");

                List<ASNLineV2> asnLineList = new ArrayList<>();
                for (SupplierInvoiceLine line : dbIBOrder.getSupplierInvoiceLines()) {
                    ASNLineV2 asnLine = new ASNLineV2();

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
            spList = new CopyOnWriteArrayList<ASNV2>(inboundList);
            log.info("There is no Supplier Invoice record found to process (sql) ...Waiting..");
        }

        if (inboundList != null) {
            log.info("Latest Supplier Invoice found: " + inboundList);
            for (ASNV2 inbound : spList) {
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
                SaleOrderReturnV2 saleOrderReturn = new SaleOrderReturnV2();
                SOReturnHeaderV2 salesReturnHeader = new SOReturnHeaderV2();

                salesReturnHeader.setCompanyCode(dbIBOrder.getCompanyCode());
                salesReturnHeader.setBranchCode(dbIBOrder.getBranchCodeOfReceivingWarehouse());
                salesReturnHeader.setTransferOrderNumber(dbIBOrder.getReturnOrderNo());
                salesReturnHeader.setMiddlewareId(dbIBOrder.getSalesReturnHeaderId());
                salesReturnHeader.setMiddlewareTable("IB_SALE_RETURN");

                List<SOReturnLineV2> salesReturnLineList = new ArrayList<>();
                for (SalesReturnLine line : dbIBOrder.getSalesReturnLines()) {
                    SOReturnLineV2 salesReturnLine = new SOReturnLineV2();

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
            spSRTList = new CopyOnWriteArrayList<SaleOrderReturnV2>(inboundSRTList);
            log.info("There is no Sales Return record found to process (sql) ...Waiting..");
        }

        if (inboundSRTList != null) {
            log.info("Latest Sales Return found: " + inboundSRTList);
            for (SaleOrderReturnV2 inbound : spSRTList) {
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

                InterWarehouseTransferInV2 interWarehouseTransferIn = new InterWarehouseTransferInV2();
                List<InterWarehouseTransferInLineV2> interWarehouseTransferInLineList = new ArrayList<>();

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

                    InterWarehouseTransferInHeaderV2 interWarehouseTransferInHeader = new InterWarehouseTransferInHeaderV2();

                    interWarehouseTransferInHeader.setToCompanyCode(dbIBOrder.getTargetCompanyCode());
                    interWarehouseTransferInHeader.setToBranchCode(dbIBOrder.getTargetBranchCode());
                    interWarehouseTransferInHeader.setTransferOrderNumber(dbIBOrder.getTransferOrderNo());
                    interWarehouseTransferInHeader.setMiddlewareId(dbIBOrder.getTransferInHeaderId());
                    interWarehouseTransferInHeader.setMiddlewareTable("IB_IWT");

                    for (TransferInLine line : dbIBOrder.getTransferInLines()) {

                        InterWarehouseTransferInLineV2 interWarehouseTransferInLine = new InterWarehouseTransferInLineV2();

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
                spIWTList = new CopyOnWriteArrayList<InterWarehouseTransferInV2>(inboundIWTList);
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
            for (InterWarehouseTransferInV2 inbound : spIWTList) {
                try {
                    log.info("InterWarehouse Transfer Order Number : " + inbound.getInterWarehouseTransferInHeader().getTransferOrderNumber());
                    WarehouseApiResponse inboundHeader = interWarehouseTransferInV2Service.postIWTTransferIn(inbound);
                    if (inboundHeader != null) {
                        // Updating the Processed Status
                        interWarehouseTransferInV2Service.updateProcessedInboundOrder(inbound.getInterWarehouseTransferInHeader().getTransferOrderNumber());
                        inboundIWTList.remove(inbound);
                        return inboundHeader;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Error on inbound processing : " + e.toString());
                    // Updating the Processed Status
                    interWarehouseTransferInV2Service.updateProcessedInboundOrder(inbound.getInterWarehouseTransferInHeader().getTransferOrderNumber());
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
            ReturnPOV2 returnPO = new ReturnPOV2();
            for (PurchaseReturnHeader dbObOrder : purchaseReturns) {
                ReturnPOHeaderV2 retPOHeader = new ReturnPOHeaderV2();

                retPOHeader.setCompanyCode(dbObOrder.getCompanyCode());
                retPOHeader.setPoNumber(dbObOrder.getReturnOrderNo());
                retPOHeader.setStoreID(dbObOrder.getBranchCode());
                retPOHeader.setRequiredDeliveryDate(String.valueOf(dbObOrder.getReturnOrderDate()));
                retPOHeader.setMiddlewareId(dbObOrder.getPurchaseReturnHeaderId());
                retPOHeader.setMiddlewareTable("OB_PURCHASE_RETURN_HEADER");

                List<ReturnPOLineV2> returnPOLineList = new ArrayList<>();
                for (PurchaseReturnLine line : dbObOrder.getPurchaseReturnLines()) {
                    ReturnPOLineV2 retPOLine = new ReturnPOLineV2();

                    retPOLine.setLineReference(line.getLineNoOfEachItemCode());
                    retPOLine.setSku(line.getItemCode());
                    retPOLine.setSkuDescription(line.getItemDescription());
                    retPOLine.setReturnQty(line.getReturnOrderQty());
                    retPOLine.setUom(line.getUnitOfMeasure());
                    retPOLine.setManufacturerCode(line.getManufacturerCode());
                    retPOLine.setManufacturerName(line.getManufacturerShortName());
                    retPOLine.setMiddlewareId(line.getPurchaseReturnLineId());
                    retPOLine.setMiddlewareHeaderId(dbObOrder.getPurchaseReturnHeaderId());
                    retPOLine.setMiddlewareTable("OB_PURCHASE_RETURN_LINE");
                    returnPOLineList.add(retPOLine);
                }
                returnPO.setReturnPOLine(returnPOLineList);
                returnPO.setReturnPOHeader(retPOHeader);
                outboundRPOList.add(returnPO);
            }
            spRPOList = new CopyOnWriteArrayList<ReturnPOV2>(outboundRPOList);
            log.info("There is no Purchase Return record found to process (sql) ...Waiting..");
        }

        if (outboundRPOList != null) {
            log.info("Latest Purchase Return found: " + outboundRPOList);
            for (ReturnPOV2 outbound : spRPOList) {
                try {
                    log.info("Purchase Return Number: " + outbound.getReturnPOHeader().getPoNumber());
                    WarehouseApiResponse response = returnPOV2Service.postReturnPOV2(outbound);
                    if (response != null) {
                        //Updating the Processed Status
                        returnPOV2Service.updateProcessedOutboundOrder(outbound.getReturnPOHeader().getPoNumber());
                        outboundRPOList.remove(outbound);
                        return response;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Error on outbound processing: " + e.toString());
                    //Updating the Processed Status
                    returnPOV2Service.updateProcessedOutboundOrder(outbound.getReturnPOHeader().getPoNumber());
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

                ShipmentOrderV2 shipmentOrder = new ShipmentOrderV2();
                List<SOLineV2> soV2List = new ArrayList<>();

                InterWarehouseTransferOutV2 iWhTransferOut = new InterWarehouseTransferOutV2();
                List<InterWarehouseTransferOutLineV2> iWhtOutLineList = new ArrayList<>();

                if (!sourceBranchExist && targetBranchExist) {
                    SOHeaderV2 soHeader = new SOHeaderV2();

                    soHeader.setCompanyCode(dbObOrder.getTargetCompanyCode());
                    soHeader.setBranchCode(dbObOrder.getTargetBranchCode());
                    soHeader.setTransferOrderNumber(dbObOrder.getTransferOrderNumber());
                    soHeader.setRequiredDeliveryDate(String.valueOf(dbObOrder.getTransferOrderDate()));
                    soHeader.setStoreID(dbObOrder.getTargetBranchCode());
                    soHeader.setMiddlewareId(dbObOrder.getTransferOutHeaderId());
                    soHeader.setMiddlewareTable("OB_SHIPMENT_ORDER_HEADER");

                    for (TransferOutLine line : dbObOrder.getTransferOutLines()) {
                        SOLineV2 soLine = new SOLineV2();

                        soLine.setLineReference(line.getLineNumberOfEachItem());
                        soLine.setSku(line.getItemCode());
                        soLine.setSkuDescription(line.getItemDescription());
                        soLine.setOrderedQty(line.getTransferOrderQty());
                        soLine.setUom(line.getUnitOfMeasure());
                        soLine.setManufacturerCode(line.getManufacturerCode());
                        soLine.setManufacturerName(line.getManufacturerShortName());
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
                    InterWarehouseTransferOutHeaderV2 iWhtOutHeader = new InterWarehouseTransferOutHeaderV2();

                    iWhtOutHeader.setFromCompanyCode(dbObOrder.getSourceCompanyCode());
                    iWhtOutHeader.setToCompanyCode(dbObOrder.getTargetCompanyCode());
                    iWhtOutHeader.setTransferOrderNumber(dbObOrder.getTransferOrderNumber());
                    iWhtOutHeader.setFromBranchCode(dbObOrder.getSourceBranchCode());
                    iWhtOutHeader.setToBranchCode(dbObOrder.getTargetBranchCode());
                    iWhtOutHeader.setRequiredDeliveryDate(String.valueOf(dbObOrder.getTransferOrderDate()));
                    iWhtOutHeader.setMiddlewareId(dbObOrder.getTransferOutHeaderId());
                    iWhtOutHeader.setMiddlewareTable("OB_IWHTRANSFER_OUT_HEADER");

                    for (TransferOutLine line : dbObOrder.getTransferOutLines()) {
                        InterWarehouseTransferOutLineV2 iWhtOutLine = new InterWarehouseTransferOutLineV2();

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
                spSOList = new CopyOnWriteArrayList<ShipmentOrderV2>(outboundSOList);
                log.info("There is no IWhTOut record found to process (sql) ...Waiting..");
            }
            if (outboundIWhtList != null) {
                spIWhtList = new CopyOnWriteArrayList<InterWarehouseTransferOutV2>(outboundIWhtList);
                log.info("There is no SO record found to process (sql) ...Waiting..");
            }
        }

        if (outboundSOList != null) {
            log.info("Latest Shipment Order found: " + outboundSOList);
            for (ShipmentOrderV2 outbound : spSOList) {
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
            for (InterWarehouseTransferOutV2 outbound : spIWhtList) {
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
            SalesOrderV2 salesOrder = new SalesOrderV2();
            for (PickListHeader dbOBOrder : pickListHeaders) {

                SalesOrderHeaderV2 salesOrderHeader = new SalesOrderHeaderV2();

                salesOrderHeader.setSalesOrderNumber(dbOBOrder.getSalesOrderNo());
                salesOrderHeader.setCompanyCode(dbOBOrder.getCompanyCode());
                salesOrderHeader.setBranchCode(dbOBOrder.getBranchCode());
                salesOrderHeader.setPickListNumber(dbOBOrder.getPickListNo());
//                salesOrderHeader.set(dbOBOrder.getPickListdate());
                salesOrderHeader.setMiddlewareTable("OB_SalesOrder");

                List<SalesOrderLineV2> salesOrderLineV2s = new ArrayList<>();
                for (PickListLine line : dbOBOrder.getPickListLines()) {
                    SalesOrderLineV2 salesOrderLineV2 = new SalesOrderLineV2();

                    salesOrderLineV2.setSku(line.getItemCode());
                    salesOrderLineV2.setSkuDescription(line.getItemDescription());
                    salesOrderLineV2.setManufacturerCode(line.getManufacturerCode());
                    salesOrderLineV2.setManufacturerName(line.getManufacturerShortName());
                    salesOrderLineV2.setUom(line.getUnitOfMeasure());
                    salesOrderLineV2.setOrderedQty(line.getPickListQty());
                    salesOrderLineV2.setManufacturerFullName(line.getManufacturerFullName());
                    salesOrderLineV2.setMiddlewareId(line.getPickListLineId());
                    salesOrderLineV2.setMiddlewareHeaderId(dbOBOrder.getPickListHeaderId());
                    salesOrderLineV2.setMiddlewareTable("OB_SalesOrder");

                    salesOrderLineV2s.add(salesOrderLineV2);
                }
                salesOrder.setSalesOrderHeader(salesOrderHeader);
                salesOrder.setSalesOrderLine(salesOrderLineV2s);
                outboundSalesOrderList.add(salesOrder);
            }
            spSalesOrderList = new CopyOnWriteArrayList<SalesOrderV2>(outboundSalesOrderList);
            log.info("There is no record found to process (sql) ...Waiting..");
        }

        if (outboundSalesOrderList != null) {
            log.info("Latest Supplier Invoice found: " + outboundSalesOrderList);
            for (SalesOrderV2 outbound : spSalesOrderList) {
                try {
                    log.info("Supplier Invoice Number : " + outbound.getSalesOrderHeader().getSalesOrderNumber());
                    WarehouseApiResponse outboundHeader = salesOrderV2Service.postSalesOrder(outbound);
                    if (outboundHeader != null) {
                        // Updating the Processed Status
                        salesOrderV2Service.updateProcessedInboundOrder(outbound.getSalesOrderHeader().getSalesOrderNumber());
                        outboundSOList.remove(outbound);
                        return outboundHeader;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Error on outbound processing : " + e.toString());
                    // Updating the Processed Status
                    salesOrderV2Service.updateProcessedInboundOrder(outbound.getSalesOrderHeader().getSalesOrderNumber());
//                    salesOrderV2Service.createInboundIntegrationLog(outbound);
                    inboundList.remove(outbound);
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
                salesInvoice.setSalesOrderNumber(dbOBOrder.getSalesOrderNumber());
                salesInvoice.setSalesInvoiceNumber(dbOBOrder.getSalesInvoiceNumber());
                salesInvoice.setPickListNumber(dbOBOrder.getPickListNumber());
                salesInvoice.setInvoiceDate(String.valueOf(dbOBOrder.getInvoiceDate()));
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
                    inboundList.remove(outbound);
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

}