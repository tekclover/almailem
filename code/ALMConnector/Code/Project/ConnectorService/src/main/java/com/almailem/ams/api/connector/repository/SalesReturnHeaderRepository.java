package com.almailem.ams.api.connector.repository;


import com.almailem.ams.api.connector.model.salesreturn.SalesReturnHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesReturnHeaderRepository extends JpaRepository<SalesReturnHeader, String> {
    SalesReturnHeader findByReturnOrderNo(String returnOrderNo);

    List<SalesReturnHeader> findTopByProcessedStatusIdOrderByOrderReceivedOn(long l);

    SalesReturnHeader findTopBySalesReturnHeaderIdAndCompanyCodeAndBranchCodeAndReturnOrderNoOrderByOrderReceivedOnDesc(
            Long salesReturnHeaderId, String companyCode, String branchCode, String returnOrderNo);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE SALESRETURNHEADER set processedStatusId = :processedStatusId, orderProcessedOn = getdate()  \r\n"
            + " WHERE SalesReturnHeaderId = :salesReturnHeaderId ", nativeQuery = true)
    public void updateProcessStatusId (
            @Param(value = "salesReturnHeaderId") Long salesReturnHeaderId,
            @Param(value = "processedStatusId") Long processedStatusId );
}
