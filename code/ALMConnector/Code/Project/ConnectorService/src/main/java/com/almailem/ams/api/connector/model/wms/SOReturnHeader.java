package com.almailem.ams.api.connector.model.wms;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class SOReturnHeader {

	@NotBlank(message = "Company Code is mandatory")
	private String companyCode;
	@NotBlank(message = "Branch Code is mandatory")
	private String branchCode;
	@NotBlank(message = "Transfer Order Number is mandatory")
	private String transferOrderNumber;

	private String isCompleted;
	private Date updatedOn;
	private String isCancelled;

	//MiddleWare Fields
	private Long middlewareId;
	private String middlewareTable;

}