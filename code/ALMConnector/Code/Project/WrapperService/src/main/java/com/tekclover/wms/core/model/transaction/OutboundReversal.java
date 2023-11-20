package com.tekclover.wms.core.model.transaction;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OutboundReversal {

	private String languageId;
	private String companyCodeId;
	private String plantId;
	private String warehouseId;
	private String outboundReversalNo;
	private String reversalType;
	private String refDocNumber;
	private String partnerCode;
	private String itemCode;
	private String packBarcode;
	private Double reversedQty;
	private Long statusId;
	private String referenceField1;
	private String referenceField2;
	private String referenceField3;
	private String referenceField4;
	private String referenceField5;
	private String referenceField6;
	private String referenceField7;
	private String referenceField8;
	private String referenceField9;
	private String referenceField10;
	private Long deletionIndicator;
	private String reversedBy;
	private Date reversedOn = new Date();

	private List<OutboundReversal> addOutboundReversal;
}
