package com.almailem.ams.api.connector.service;


import com.almailem.ams.api.connector.model.salesreturn.SalesReturnHeader;
import com.almailem.ams.api.connector.model.salesreturn.SalesReturnLine;
import com.almailem.ams.api.connector.model.stockreceipt.StockReceiptHeader;
import com.almailem.ams.api.connector.model.stockreceipt.StockReceiptLine;
import com.almailem.ams.api.connector.model.supplierinvoice.SupplierInvoiceHeader;
import com.almailem.ams.api.connector.model.supplierinvoice.SupplierInvoiceLine;
import com.almailem.ams.api.connector.model.transferin.TransferInHeader;
import com.almailem.ams.api.connector.model.transferin.TransferInLine;
import com.almailem.ams.api.connector.model.wms.*;
import com.almailem.ams.api.connector.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class InboundService {

    @Autowired
    SupplierInvoiceService supplierInvoiceService;

    @Autowired
    StockReceiptService stockReceiptService;

    @Autowired
    SalesReturnService salesReturnService;

//    @Autowired
//    B2BTransferInService b2BTransferInService;

    @Autowired
    InterWarehouseTransferInV2Service interWarehouseTransferInV2Service;

    //-------------------------------------------------------------------------------------------

    @Autowired
    SupplierInvoiceHeaderRepository supplierInvoiceHeaderRepository;

    @Autowired
    StockReceiptHeaderRepository stockReceiptHeaderRepository;

    @Autowired
    SalesReturnHeaderRepository salesReturnHeaderRepository;

//    @Autowired
//    B2BInHeaderRepository b2BInHeaderRepository;

    @Autowired
    TransferInHeaderRepository transferInHeaderRepository;

    //-------------------------------------------------------------------------------------------
    List<ASNV2> inboundList = null;
    List<com.almailem.ams.api.connector.model.wms.StockReceiptHeader> inboundSRList = null;
    List<SaleOrderReturnV2> inboundSRTList = null;
    List<B2bTransferIn> inboundB2BList = null;
    List<InterWarehouseTransferInV2> inboundIWTList = null;
    static CopyOnWriteArrayList<ASNV2> spList = null;                               // ASN Inbound List
    static CopyOnWriteArrayList<com.almailem.ams.api.connector.model.wms.StockReceiptHeader> spSRList = null;                // StockReceipt Inbound List
    static CopyOnWriteArrayList<SaleOrderReturnV2> spSRTList = null;                // SaleOrder Inbound List
    static CopyOnWriteArrayList<B2bTransferIn> spB2BList = null;                    // B2B Inbound List
    static CopyOnWriteArrayList<InterWarehouseTransferInV2> spIWTList = null;       // InterWarehouse Inbound List

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
                asnHeader.setPurchaseOrderNumber(dbIBOrder.getPurchaseOrderNo());
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
            log.info("There is no record found to process (sql) ...Waiting..");
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
            log.info("There is no record found to process (sql) ...Waiting..");
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
                    if(line.getNoOfPacks() != null) {
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
            log.info("There is no record found to process (sql) ...Waiting..");
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

    //=====================================================b2b============================================================
//    public WarehouseApiResponse processInboundOrderB2B() throws IllegalAccessException, InvocationTargetException {
//        if (inboundB2BList == null || inboundB2BList.isEmpty()) {
//            List<B2BHeader> b2BHeaders = b2BInHeaderRepository.findTopByProcessedStatusIdOrderByOrderReceivedOn(0L);
//            inboundB2BList = new ArrayList<>();
//            for (B2BHeader dbIBOrder : b2BHeaders) {
//                B2bTransferIn b2bTransferIn = new B2bTransferIn();
//                B2bTransferInHeader b2bTransferInHeader = new B2bTransferInHeader();
//
//                b2bTransferInHeader.setCompanyCode(dbIBOrder.getTargetCompanyCode());
//                b2bTransferInHeader.setBranchCode(dbIBOrder.getTargetBranchCode());
//                b2bTransferInHeader.setTransferOrderNumber(dbIBOrder.getTransferOrderNo());
//                b2bTransferInHeader.setMiddlewareId(dbIBOrder.getB2bInHeaderId());
//                b2bTransferInHeader.setMiddlewareTable("IB_B2B");
//
//                List<B2bTransferInLine> b2bTransferInLines = new ArrayList<>();
//                for (B2BLine line : dbIBOrder.getB2BLines()) {
//                    B2bTransferInLine b2bTransferInLine = new B2bTransferInLine();
//
//                    b2bTransferInLine.setLineReference(line.getLineNoForEachItem());
//                    b2bTransferInLine.setSku(line.getItemCode());
//                    b2bTransferInLine.setSkuDescription(line.getItemDescription());
//                    b2bTransferInLine.setManufacturerName(line.getManufacturerShortName());
//                    b2bTransferInLine.setExpectedQty(line.getTransferQty());
//                    b2bTransferInLine.setUom(line.getUnitOfMeasure());
//                    b2bTransferInLine.setManufacturerCode(line.getManufacturerCode());
//                    b2bTransferInLine.setManufacturerFullName(line.getManufacturerFullName());
//                    b2bTransferInLine.setExpectedDate(String.valueOf(dbIBOrder.getTransferOrderDate()));
//                    b2bTransferInLine.setStoreID(dbIBOrder.getTargetBranchCode());
//                    b2bTransferInLine.setOrigin(dbIBOrder.getSourceCompanyCode());
//                    b2bTransferInLine.setBrand(line.getManufacturerShortName());
//                    if(line.getTransferQty() != null) {
//                        b2bTransferInLine.setPackQty(Long.valueOf(String.valueOf(line.getTransferQty())));
//                    }
//                    b2bTransferInLine.setMiddlewareId(line.getB2bInLineId());
//                    b2bTransferInLine.setMiddlewareHeaderId(dbIBOrder.getB2bInHeaderId());
//                    b2bTransferInLine.setMiddlewareTable("IB_B2B");
//
//                    b2bTransferInLines.add(b2bTransferInLine);
//                }
//                b2bTransferIn.setB2bTransferInHeader(b2bTransferInHeader);
//                b2bTransferIn.setB2bTransferLine(b2bTransferInLines);
//                inboundB2BList.add(b2bTransferIn);
//            }
//            spB2BList = new CopyOnWriteArrayList<B2bTransferIn>(inboundB2BList);
//            log.info("There is no record found to process (sql) ...Waiting..");
//        }
//
//        if (inboundB2BList != null) {
//            log.info("Latest B2B Transfer found: " + inboundB2BList);
//            for (B2bTransferIn inbound : spB2BList) {
//                try {
//                    log.info("B2B Transfer Order Number : " + inbound.getB2bTransferInHeader().getTransferOrderNumber());
//                    WarehouseApiResponse inboundHeader = b2BTransferInService.postB2BTransferIn(inbound);
//                    if (inboundHeader != null) {
//                        // Updating the Processed Status
//                        b2BTransferInService.updateProcessedInboundOrder(inbound.getB2bTransferInHeader().getTransferOrderNumber());
//                        inboundB2BList.remove(inbound);
//                        return inboundHeader;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    log.error("Error on inbound processing : " + e.toString());
//                    // Updating the Processed Status
//                    b2BTransferInService.updateProcessedInboundOrder(inbound.getB2bTransferInHeader().getTransferOrderNumber());
//                    b2BTransferInService.createInboundIntegrationLog(inbound);
//                    inboundB2BList.remove(inbound);
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//        return null;
//    }

    //=====================================================Interwarehouse============================================================
    public WarehouseApiResponse processInboundOrderIWT() throws IllegalAccessException, InvocationTargetException {
        if (inboundIWTList == null || inboundIWTList.isEmpty()) {
            List<TransferInHeader> transferInHeaders = transferInHeaderRepository.findTopByProcessedStatusIdOrderByOrderReceivedOn(0L);
            inboundIWTList = new ArrayList<>();
            for (TransferInHeader dbIBOrder : transferInHeaders) {
                InterWarehouseTransferInV2 interWarehouseTransferIn = new InterWarehouseTransferInV2();
                InterWarehouseTransferInHeaderV2 interWarehouseTransferInHeader = new InterWarehouseTransferInHeaderV2();

                interWarehouseTransferInHeader.setToCompanyCode(dbIBOrder.getTargetCompanyCode());
                interWarehouseTransferInHeader.setToBranchCode(dbIBOrder.getTargetBranchCode());
                interWarehouseTransferInHeader.setTransferOrderNumber(dbIBOrder.getTransferOrderNo());
                interWarehouseTransferInHeader.setMiddlewareId(dbIBOrder.getTransferInHeaderId());
                interWarehouseTransferInHeader.setMiddlewareTable("IB_IWT");

                List<InterWarehouseTransferInLineV2> interWarehouseTransferInLineList = new ArrayList<>();
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
            spIWTList = new CopyOnWriteArrayList<InterWarehouseTransferInV2>(inboundIWTList);
            log.info("There is no record found to process (sql) ...Waiting..");
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

}
