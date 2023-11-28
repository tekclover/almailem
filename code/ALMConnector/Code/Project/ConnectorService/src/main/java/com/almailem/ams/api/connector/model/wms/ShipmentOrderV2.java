package com.almailem.ams.api.connector.model.wms;

import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class ShipmentOrderV2 {
	
	@Valid
	private SOHeaderV2 soHeader;
	
	@Valid
	private List<SOLineV2> soLine;
}
