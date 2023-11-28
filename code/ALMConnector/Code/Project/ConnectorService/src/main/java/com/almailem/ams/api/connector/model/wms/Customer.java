package com.almailem.ams.api.connector.model.wms;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class Customer {

    @NotBlank(message = "Company Code is mandatory")
    private String companyCode;

    private String branchCode;

    @NotBlank(message = "Partner Code is mandatory")
    private String partnerCode;

    @NotBlank(message = "Partner Name is mandatory")
    private String partnerName;

    @NotBlank(message = "Address1 is mandatory")
    private String address1;

    private String address2;

    private String phoneNumber;

    private String civilId;

    private String country;

    private String alternatePhoneNumber;

    @NotBlank(message = "Created By is mandatory")
    private String createdBy;

    @NotBlank(message = "Created On Date is mandatory")
    private String createdOn;

    private String isNew;
    private String isUpdate;
    private String isCompleted;
    private Date updatedOn;

    //MiddleWare Fields
    private Long middlewareId;
    private String middlewareTable;
}
