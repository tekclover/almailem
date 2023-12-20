package com.almailem.ams.api.connector.model.wms;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SalesOrderLine {

    @NotNull(message = "Line Reference is mandatory")
    private Long lineReference;                                // IB_LINE_NO

    @NotBlank(message = "SKU is mandatory")
    private String sku;                                    // ITM_CODE

    private String skuDescription;                            // ITEM_TEXT

    @NotNull(message = "Ordered Quantity is mandatory")
    private Double orderedQty;                                // ORD_QTY

    @NotBlank(message = "Unit of Measure is mandatory")
    private String uom;                                        // ORD_UOM

    @NotBlank(message = "Manufacturer Code is mandatory")
    private String manufacturerCode;

    @NotBlank(message = "Manufacturer Name is mandatory")
    private String manufacturerName;

    private String brand;

    private String salesOrderNo;
    private String pickListNo;

    private String origin;
    private String supplierName;
    private Double packQty;
    private String fromCompanyCode;
    private Double expectedQty;
    protected String storeID;
    private String sourceBranchCode;
    private String countryOfOrigin;
    //almailem fields

    private String manufacturerFullName;
    //MiddleWare Fields
    private Long middlewareId;
    private Long middlewareHeaderId;
    private String middlewareTable;
}
