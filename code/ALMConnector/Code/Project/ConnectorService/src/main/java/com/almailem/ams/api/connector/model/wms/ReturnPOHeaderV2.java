package com.almailem.ams.api.connector.model.wms;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReturnPOHeaderV2 {

    @NotBlank(message = "PO Number is mandatory")
    private String poNumber;                                       // REF_DOC_NO;

    @NotBlank(message = "StoreId is mandatory")
    private String storeID;                                        // PARTNER_CODE;

//    private String storeName;                                    // PARTNER_NM;

    @NotBlank(message = "Required Delivery Date is mandatory")
    private String requiredDeliveryDate;                           //REQ_DEL_DATE

    @NotBlank(message = "CompanyCode is mandatory")
    private String companyCode;

    //MiddleWare Fields
    private Long middlewareId;
    private String middlewareTable;
}
