package com.almailem.ams.api.connector.model.wms;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReturnPOLineV2 {

    @NotNull(message = "Line Reference is mandatory")
    private Long lineReference;                                // IB_LINE_NO

    @NotBlank(message = "SKU is mandatory")
    private String sku;                                    // ITM_CODE

    private String skuDescription;                            // ITEM_TEXT

    @NotNull(message = "Return Qty is mandatory")
    private Double returnQty;                                // ORD_QTY

    @NotBlank(message = "UOM is mandatory")
    private String uom;                                        // ORD_UOM

    @NotBlank(message = "Manufacturer Code is mandatory")
    private String manufacturerCode;

    @NotBlank(message = "Manufacturer Name is mandatory")
    private String manufacturerName;

    //MiddleWare Fields
    private Long middlewareId;
    private Long middlewareHeaderId;
    private String middlewareTable;
}
