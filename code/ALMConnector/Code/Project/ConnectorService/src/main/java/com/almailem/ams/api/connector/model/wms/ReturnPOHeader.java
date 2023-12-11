package com.almailem.ams.api.connector.model.wms;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class ReturnPOHeader {

    @NotBlank(message = "PO Number is mandatory")
    private String poNumber;                                       // REF_DOC_NO;

    @NotBlank(message = "StoreId is mandatory")
    private String storeID;                                        // PARTNER_CODE;

    private String storeName;                                    // PARTNER_NM;

    @NotBlank(message = "Required Delivery Date is mandatory")
    private String requiredDeliveryDate;                           //REQ_DEL_DATE

    @NotBlank(message = "CompanyCode is mandatory")
    private String companyCode;

    private String wareHouseId;                            // WH_ID
    private String branchCode;
    private String languageId;

    @JsonIgnore
    private String id;

    @JsonIgnore
    private Date orderReceivedOn;

    @JsonIgnore
    private Long statusId;

    //MiddleWare Fields
//    private String supplierInvoiceNo;

    //MiddleWare Fields
    private Long middlewareId;
    private String middlewareTable;
}