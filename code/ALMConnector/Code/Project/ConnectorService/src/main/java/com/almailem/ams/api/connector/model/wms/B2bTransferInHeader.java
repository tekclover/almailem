package com.almailem.ams.api.connector.model.wms;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class B2bTransferInHeader {

	@NotBlank(message = "Company Code is mandatory")
	private String companyCode;
	@NotBlank(message = "Transfer Order Number is mandatory")
	private String transferOrderNumber;
	@NotBlank(message = "Branch Code is mandatory")
	private String branchCode;

	//MiddleWare Fields
	private Long middlewareId;
	private String middlewareTable;
}