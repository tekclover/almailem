package com.almailem.ams.api.connector.repository;

import com.almailem.ams.api.connector.model.salesinvoice.SalesInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface SalesInvoiceRepository extends JpaRepository<SalesInvoice, String> {

    List<SalesInvoice> findTopByProcessedStatusIdOrderByOrderReceivedOn(Long l);

    SalesInvoice findBySalesInvoiceNumber(String asnNumber);

    SalesInvoice findTopBySalesInvoiceNumberOrderByOrderReceivedOnDesc(String asnNumber);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE SALESINVOICE set processedStatusId = 10, orderProcessedOn = :date  \r\n"
            + " WHERE salesInvoiceNumber = :salesInvoiceNumber ", nativeQuery = true)
    public void updateProcessStatusId (
            @Param(value = "salesInvoiceNumber") String salesInvoiceNumber,
            @Param(value = "date") Date date);
}
