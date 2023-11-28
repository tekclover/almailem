package com.almailem.ams.api.connector.model.wms;

import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class SalesOrderV2 {
	
	@Valid
	private SalesOrderHeaderV2 salesOrderHeader;
	
	@Valid
	private List<SalesOrderLineV2> salesOrderLine;
}
