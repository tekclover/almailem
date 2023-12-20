package com.almailem.ams.api.connector.repository;


import com.almailem.ams.api.connector.model.transferout.TransferOutHeader;
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
public interface TransferOutHeaderRepository extends JpaRepository<TransferOutHeader, String>,
        JpaSpecificationExecutor<TransferOutHeader> {

    List<TransferOutHeader> findTopByProcessedStatusIdOrderByOrderReceivedOn(long l);

    TransferOutHeader findByTransferOrderNumber(String transferOrderNumber);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE TRANSFEROUTHEADER set processedStatusId = 10, orderProcessedOn = :date \r\n"
            + "WHERE TransferOrdernumber = :transferOrderNumber", nativeQuery = true)
    public void updateProcessStatusId(@Param(value = "transferOrderNumber") String transferOrderNumber,
                                      @Param(value = "date") Date date);

    TransferOutHeader findTopByTransferOrderNumberOrderByOrderReceivedOnDesc(String transferOrderNumber);

    List<TransferOutHeader> findTopByProcessedStatusIdOrderByOrderReceivedOnDesc(long l);
}
