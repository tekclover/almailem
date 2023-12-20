package com.almailem.ams.api.connector.repository;

import com.almailem.ams.api.connector.model.perpetual.PerpetualHeader;
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
public interface PerpetualHeaderRepository extends JpaRepository<PerpetualHeader, String>, JpaSpecificationExecutor<PerpetualHeader> {
    List<PerpetualHeader> findTopByProcessedStatusIdOrderByOrderReceivedOn(long l);

    PerpetualHeader findTopByCycleCountNoOrderByOrderReceivedOnDesc(String cycleCountNo);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE PERPETUALHEADER set processedStatusId = 10, orderProcessedOn = :date \r\n"
            + " WHERE CycleCountNo = :cycleCountNo ", nativeQuery = true)
    void updateProcessStatusId(@Param(value = "cycleCountNo") String cycleCountNo,
                               @Param(value = "date") Date date);

}