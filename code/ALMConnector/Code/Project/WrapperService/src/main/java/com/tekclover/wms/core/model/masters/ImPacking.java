package com.tekclover.wms.core.model.masters;

import java.util.Date;
import lombok.Data;

@Data

public class ImPacking {

	private String packingMaterialNo;
	private String languageId;
	private String companyCodeId;
	private String plantId;
	private String warehouseId;
	private String itemCode;
	private Boolean packingIndicator;
	private String description ;
	private Double packQtyPerCarton;
	private Boolean shrinkWrap;
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
	private String createdBy;
	private Date createdOn = new Date();
	private String updatedBy;
	private Date updatedOn = new Date();

}