package com.tekclover.wms.core.model.masters;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SearchHandlingUnit {
	/*
	 * WH_ID
	 * HU_UNIT
	 * STATUS_ID
	 * REF_FIELD_1
	 * CTD_BY
	 * CTD_ON
	 * UTD_BY
	 * UTD_ON
	 */
	 
	private List<String> warehouseId;
	private List<String>companyCodeId;
	private List<String>plantId;
	private List<String> handlingUnit;
	private List<Long> statusId;
	private List<String>languageId;
	private List<String> referenceField1;
	
	private List<String> createdBy;
	private List<String> updatedBy;
	
	private Date startCreatedOn;
	private Date endCreatedOn;
	
	private Date startUpdatedOn;
	private Date endUpdatedOn;	

}
