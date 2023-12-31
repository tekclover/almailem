package com.tekclover.wms.core.model.transaction;

import lombok.Data;

@Data
public class InventoryV2 extends Inventory {

	private String barcodeId;
	private String cbm;
	private String cbmUnit;
	private String cbmPerQuantity;
	private String manufacturerCode;
	private String manufacturerName;
	private String origin;
	private String brand;
	private String referenceDocumentNo;
	private String companyDescription;
	private String plantDescription;
	private String warehouseDescription;
	private String statusDescription;

}
