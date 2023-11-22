package com.almailem.ams.api.connector.model.wms;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class StockReceiptHeader {

    private String companyCode;
    private String branchCode;
    private String receiptNo;
    private String isCompleted;
    private Date updatedOn;

    //ProcessedStatusIdOrderByOrderReceivedOn
    private Long processedStatusId = 0L;
    private Date orderReceivedOn;
    private Date orderProcessedOn;

    //MiddleWare Fields
    private Long middlewareId;
    private String middlewareTable;

    private List<StockReceiptLine> stockReceiptLines;
}
