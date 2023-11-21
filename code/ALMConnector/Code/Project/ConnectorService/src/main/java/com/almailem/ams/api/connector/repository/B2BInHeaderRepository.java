package com.almailem.ams.api.connector.repository;

import com.almailem.ams.api.connector.model.b2b.B2BHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface B2BInHeaderRepository extends JpaRepository<B2BHeader, Long>, JpaSpecificationExecutor<B2BHeader> {

    B2BHeader findByTransferOrderNo(String asnNumber);

    List<B2BHeader> findTopByProcessedStatusIdOrderByOrderReceivedOn(Long deletionIndicator);
}
