package com.almailem.ams.api.connector.model.wms;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PerpetualLineV1 {

    @NotBlank(message = "CycleCountNo is mandatory")
    private String cycleCountNo;

    @NotNull(message = "Line No Of Each Item Code is mandatory")
    private Long lineNoOfEachItemCode;

    @NotBlank(message = "itemCode is mandatory")
    private String itemCode;

    private String itemDescription;

    @NotBlank(message = "UOM is mandatory")
    private String uom;

    @NotBlank(message = "Manufacturer Code is mandatory")
    private String manufacturerCode;

    @NotBlank(message = "Manufacturer Name is mandatory")
    private String manufacturerName;

    @NotNull(message = "FrozenQty is mandatory")
    private Double frozenQty;
    private Double countedQty;

    private String isCompleted;
    private String isCancelled;

    //MiddleWare Fields
    private Long middlewareId;
    private Long middlewareHeaderId;
    private String middlewareTable;
}
