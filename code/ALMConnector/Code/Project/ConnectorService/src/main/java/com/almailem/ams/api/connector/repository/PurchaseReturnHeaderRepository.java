package com.almailem.ams.api.connector.repository;

import com.almailem.ams.api.connector.model.purchasereturn.PurchaseReturnHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface PurchaseReturnHeaderRepository extends JpaRepository<PurchaseReturnHeader, String>,
        JpaSpecificationExecutor<PurchaseReturnHeader> {

    List<PurchaseReturnHeader> findTopByProcessedStatusIdOrderByOrderReceivedOn(long l);

    PurchaseReturnHeader findByReturnOrderNo(String poNumber);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE PURCHASERETURNHEADER set processedStatusId = 10, orderProcessedOn = :date  \r\n"
            + " WHERE ReturnorderNo = :returnOrderNo ", nativeQuery = true)
    public void updateProcessStatusId(
            @Param(value = "returnOrderNo") String returnOrderNo,
            @Param(value = "date") Date date);

    PurchaseReturnHeader findTopByReturnOrderNoOrderByOrderReceivedOnDesc(String returnOrderNo);
}
