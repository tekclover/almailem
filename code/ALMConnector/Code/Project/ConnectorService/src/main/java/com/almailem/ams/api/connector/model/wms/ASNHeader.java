package com.almailem.ams.api.connector.model.wms;

import javax.validation.constraints.NotBlank;

import lombok.Data;

import java.util.Date;

@Data
public class ASNHeader {

	@NotBlank(message = "Branch Code is mandatory")
	private String branchCode;
	
	@NotBlank(message = "Company Code is mandatory")
	private String companyCode;
	
	@NotBlank(message = "ASN number is mandatory")
	private String asnNumber;

	//almailem fields
//	private String purchaseOrderNumber;

	private String isCompleted;
	private Date updatedOn;
	private String isCancelled;

	//MiddleWare Fields
	private Long middlewareId;
	private String middlewareTable;
}