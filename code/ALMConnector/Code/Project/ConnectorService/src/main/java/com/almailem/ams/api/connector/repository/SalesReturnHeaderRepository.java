package com.almailem.ams.api.connector.repository;


import com.almailem.ams.api.connector.model.salesreturn.SalesReturnHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesReturnHeaderRepository extends JpaRepository<SalesReturnHeader, String> {
    SalesReturnHeader findByReturnOrderNo(String returnOrderNo);

    List<SalesReturnHeader> findTopByProcessedStatusIdOrderByOrderReceivedOn(long l);

    SalesReturnHeader findTopByReturnOrderNoOrderByOrderReceivedOnDesc(String returnOrderNo);
}
