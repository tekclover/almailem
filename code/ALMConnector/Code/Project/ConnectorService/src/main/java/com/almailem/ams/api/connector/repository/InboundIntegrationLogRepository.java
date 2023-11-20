package com.almailem.ams.api.connector.repository;

import com.almailem.ams.api.connector.model.wms.InboundIntegrationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface InboundIntegrationLogRepository extends JpaRepository<InboundIntegrationLog,Long>,
		JpaSpecificationExecutor<InboundIntegrationLog> {
	
	public List<InboundIntegrationLog> findAll();
	public Optional<InboundIntegrationLog> 
		findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndIntegrationLogNumberAndRefDocNumberAndDeletionIndicator(
				String languageId, String companyCodeId, String plantId, String warehouseId, String integrationLogNumber, String refDocNumber, Long deletionIndicator);
	public List<InboundIntegrationLog> findByIntegrationStatus(String string);
}