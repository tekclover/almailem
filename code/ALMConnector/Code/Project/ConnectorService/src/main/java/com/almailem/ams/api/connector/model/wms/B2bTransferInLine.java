package com.almailem.ams.api.connector.model.wms;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class B2bTransferInLine {

	@NotNull(message = "Line Reference is mandatory")
	private Long lineReference;
	@NotBlank(message = "sku is mandatory")
	private String sku;
	@NotBlank(message = "sku Description is mandatory")
	private String skuDescription;
	@NotBlank(message = "Store Id is mandatory")
	private String storeID;
	private String supplierPartNumber;
	private String manufacturerName;
	@NotBlank(message = "Excepted Date is mandatory")
	private String expectedDate;
	@NotNull(message = "Expected Qty is mandatory")
	private Double expectedQty;
	@NotBlank(message = "uom is mandatory")
	private String uom;
	private Long packQty;
	@NotBlank(message = "Origin is mandatory")
	private String origin;
	@NotBlank(message = "Manufacturer Code is mandatory")
	private String manufacturerCode;
	private String manufacturerFullName;
	private String brand;
	private String supplierName;
	private String transferOrderNo;
	private String isCompleted;
	//MiddleWare Fields
	private Long middlewareId;
	private Long middlewareHeaderId;
	private String middlewareTable;
}
