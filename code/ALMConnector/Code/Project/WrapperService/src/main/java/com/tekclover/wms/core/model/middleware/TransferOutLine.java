package com.tekclover.wms.core.model.middleware;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TransferOutLine {

    private Long pickListLineId;

    private Long transferOutHeaderId;

    @NotBlank(message = "TransferOrderNo is mandatory")
    private String transferOrderNumber;

    @NotNull(message = "Line Number for Each Item is mandatory")
    private Long lineNoForEachItem;

    @NotBlank(message = "Item Code is mandatory")
    private String itemCode;

    @NotBlank(message = "Item Description is mandatory")
    private String itemDescription;

    @NotNull(message = "TransferOrder Quantity is mandatory")
    private Double transferOrderQty;

    @NotBlank(message = "Manufacturer Short Name is mandatory")
    private String manufacturerShortName;

    @NotBlank(message = "Manufacturer Code is mandatory")
    private String manufacturerCode;

    @NotBlank(message = "UOM is mandatory")
    private String uom;

    private String manufacturerFullName;
}
