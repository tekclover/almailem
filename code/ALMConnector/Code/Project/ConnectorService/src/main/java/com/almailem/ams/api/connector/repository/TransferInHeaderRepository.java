package com.almailem.ams.api.connector.repository;

import com.almailem.ams.api.connector.model.transferin.TransferInHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TransferInHeaderRepository extends JpaRepository<TransferInHeader, Long>, JpaSpecificationExecutor<TransferInHeader> {

    TransferInHeader findByTransferOrderNo(String asnNumber);

    List<TransferInHeader> findTopByProcessedStatusIdOrderByOrderReceivedOn(Long deletionIndicator);
}
