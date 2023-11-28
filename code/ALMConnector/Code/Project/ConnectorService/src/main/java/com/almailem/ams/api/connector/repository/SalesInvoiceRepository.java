package com.almailem.ams.api.connector.repository;

import com.almailem.ams.api.connector.model.salesinvoice.SalesInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SalesInvoiceRepository extends JpaRepository<SalesInvoice, String> {

    List<SalesInvoice> findTopByProcessedStatusIdOrderByOrderReceivedOn(Long l);

    SalesInvoice findBySalesInvoiceNumber(String asnNumber);

    SalesInvoice findTopBySalesInvoiceNumberOrderByOrderReceivedOnDesc(String asnNumber);
}
