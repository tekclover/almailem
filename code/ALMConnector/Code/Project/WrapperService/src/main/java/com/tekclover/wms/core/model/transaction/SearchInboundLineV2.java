package com.tekclover.wms.core.model.transaction;

import com.tekclover.wms.core.model.transaction.SearchInboundLine;
import lombok.Data;

import java.util.List;

@Data
public class SearchInboundLineV2 extends SearchInboundLine {

	private List<String> languageId;
	private List<String> companyCodeId;
	private List<String> plantId;
}
