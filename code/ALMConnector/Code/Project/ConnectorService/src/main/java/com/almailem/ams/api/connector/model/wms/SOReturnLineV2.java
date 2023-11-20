package com.almailem.ams.api.connector.model.wms;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SOReturnLineV2  {

	@NotNull(message = "Line Reference is mandatory")
	private Long lineReference;
	@NotBlank(message = "sku is mandatory")
	private String sku;
	@NotBlank(message = "sku description is mandatory")
	private String skuDescription;
	@NotBlank(message = "Invoice Number is mandatory")
	private String invoiceNumber;
	private String storeID;
	private String supplierPartNumber;
	@NotBlank(message = "Manufacturer Name is mandatory")
	private String manufacturerName;
	@NotBlank(message = "Expected Date is mandatory")
	private String expectedDate;
	@NotNull(message = "expectedQty is mandatory")
	private Double expectedQty;
	@NotBlank(message = "uom is mandatory")
	private String uom;
	private Double packQty;
	private String origin;
	@NotBlank(message = "Manufacturer Code is mandatory")
	private String manufacturerCode;
	private String manufacturerFullName;
	private String brand;
	private String salesOrderReference;

	//MiddleWare Fields
	private Long middlewareId;
	private Long middlewareHeaderId;
	private String middlewareTable;

}