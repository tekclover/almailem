package com.almailem.ams.api.connector.repository;


import com.almailem.ams.api.connector.model.picklist.PickListHeader;
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
public interface PickListHeaderRepository extends JpaRepository<PickListHeader, String>, JpaSpecificationExecutor<PickListHeader> {
    List<PickListHeader> findTopByProcessedStatusIdOrderByOrderReceivedOn(long l);

    PickListHeader findByPickListNo(String asnNumber);

    PickListHeader findTopByPickListNoOrderByOrderReceivedOnDesc(String asnNumber);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE PICKLISTHEADER set processedStatusId = 10, orderProcessedOn = :date  \r\n"
            + " WHERE PickListNo = :pickListNo ", nativeQuery = true)
    public void updateProcessStatusId (
            @Param(value = "pickListNo") String pickListNo,
            @Param(value = "date") Date date);
}
