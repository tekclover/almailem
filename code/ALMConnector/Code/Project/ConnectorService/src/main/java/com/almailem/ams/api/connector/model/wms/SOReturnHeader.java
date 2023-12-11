package com.almailem.ams.api.connector.model.wms;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SOReturnHeader {

	@NotBlank(message = "Company Code is mandatory")
	private String companyCode;
	@NotBlank(message = "Branch Code is mandatory")
	private String branchCode;
	@NotBlank(message = "Transfer Order Number is mandatory")
	private String transferOrderNumber;

	//MiddleWare Fields
	private Long middlewareId;
	private String middlewareTable;

}