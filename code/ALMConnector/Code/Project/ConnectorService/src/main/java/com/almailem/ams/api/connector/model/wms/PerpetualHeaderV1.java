package com.almailem.ams.api.connector.model.wms;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class PerpetualHeaderV1 {

    @NotBlank(message = "CompanyCode is mandatory")
    private String companyCode;

    @NotBlank(message = "CycleCountNo is mandatory")
    private String cycleCountNo;

    @NotBlank(message = "BranchCode is mandatory")
    private String branchCode;

    private String branchName;

    private Date cycleCountCreationDate;

//    @NotBlank(message = "Is-New is mandatory")
    private String isNew;

    private String isCancelled;
    private String isCompleted;
    private Date updatedOn;

    //MiddleWare Fields
    private Long middlewareId;
    private String middlewareTable;
}
