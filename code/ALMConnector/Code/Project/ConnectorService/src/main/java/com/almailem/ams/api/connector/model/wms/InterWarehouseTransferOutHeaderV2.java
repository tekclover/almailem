package com.almailem.ams.api.connector.model.wms;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InterWarehouseTransferOutHeaderV2 {

    private String fromWhsID;                            // WH_ID

    private String toWhsID;                            // PARTNER_CODE

    private String storeName;                            // PARTNER_NM

    private String companyCode;

    @NotBlank(message = "From Company Code is mandatory")
    private String fromCompanyCode;

    @NotBlank(message = "To Company Code is mandatory")
    private String toCompanyCode;

    @NotBlank(message = "From Branch Code is mandatory")
    private String fromBranchCode;

    @NotBlank(message = "To Branch Code is mandatory")
    private String toBranchCode;

    @NotBlank(message = "Transfer Order Number is mandatory")
    private String transferOrderNumber;

    @NotBlank(message = "Required Delivery Date is mandatory")
    private String requiredDeliveryDate;

    private String branchCode;
    private String languageId;

    //MiddleWare Fields
    private Long middlewareId;
    private String middlewareTable;
}
